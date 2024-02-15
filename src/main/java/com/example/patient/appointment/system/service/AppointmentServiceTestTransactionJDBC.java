package com.example.patient.appointment.system.service;

import com.example.patient.appointment.system.exception.SlotIsBusyException;
import com.example.patient.appointment.system.exception.TransactionRollbackException;
import com.example.patient.appointment.system.model.Patient;
import com.example.patient.appointment.system.model.ResponseForSpecificPatient;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceTestTransactionJDBC {

    private final DataSource dataSource;

    /**
     * Занимает слот времени для указанного пациента, добавляя его в базу данных, если он не существует.
     * Если слот уже занят или указанный слот не найден, операция откатывается до точки сохранения,
     * созданной после успешного добавления пациента, что гарантирует, что информация о пациенте остается в базе данных
     * независимо от результата бронирования слота.
     *
     * @param patient объект {@link Patient}, содержащий информацию о пациенте. Должен содержать полное имя,
     *                дату рождения, пол, номер страхового полиса и, необязательно, номер телефона.
     * @param slotId  идентификатор слота времени, который необходимо забронировать для пациента.
     *                Должен быть не {@code null} и указывать на существующий слот в базе данных.
     * @return объект {@link ResponseForSpecificPatient}, содержащий информацию о результате операции:
     * был ли пациент добавлен в базу данных или уже существовал, а также был ли успешно забронирован слот.
     * В случае ошибки при бронировании слота возвращает соответствующее описание ошибки.
     * @throws SlotIsBusyException если {@code slotId} равен {@code null} или указанный слот уже занят
     *                                  или не найден в базе данных.
     */
    public ResponseForSpecificPatient bookSlotForSpecificPatient(Patient patient, Long slotId) {

        ResponseForSpecificPatient response = new ResponseForSpecificPatient(false, false, false, "");

        // Создание соединения и точки сохранения
        Connection connection = null;
        Savepoint savepoint = null;

        try {
            // Получение соединения и отключение автокоммита
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            // Проверка существования пациента
            String checkPatientSql = "SELECT id FROM patients WHERE full_name = ? AND date_of_birth = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkPatientSql)) {
                checkStmt.setString(1, patient.getFullName());
                checkStmt.setDate(2, java.sql.Date.valueOf(patient.getDateOfBirth()));
                ResultSet rs = checkStmt.executeQuery();
                // Если пациент не найден, добавляем его
                if (!rs.next()) {
                    String insertPatientSql =
                            "INSERT INTO patients (full_name, date_of_birth, gender, insurance_policy_number, created_at, updated_at, phone_number ) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertPatientSql, Statement.RETURN_GENERATED_KEYS)) {
                        insertStmt.setString(1, patient.getFullName());
                        insertStmt.setDate(2, java.sql.Date.valueOf(patient.getDateOfBirth()));
                        insertStmt.setString(3, patient.getGender().toString());
                        insertStmt.setLong(4, patient.getInsurancePolicyNumber());
                        insertStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                        insertStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                        insertStmt.setString(7, patient.getPhoneNumber());
                        insertStmt.executeUpdate();
                        response.setAdded(true);
                        // Получение ID добавленного пациента
                        rs = insertStmt.getGeneratedKeys();
                        if (rs.next()) {
                            patient.setId(rs.getLong(1));
                        }
                    }
                    // Если пациент найден, устанавливаем его ID
                } else {
                    response.setExisted(true);
                    patient.setId(rs.getLong("id"));
                }
            }
            // Создание точки сохранения как только пациент добавлен в базу данных
            savepoint = connection.setSavepoint("PatientSavedInDB");

            // В случае ошибки при бронировании слота, откатываем транзакцию до точки сохранения

            // Попытка забронировать слот
            String bookSlotSql = "UPDATE time_slots SET patient_id = ? WHERE id = ? AND patient_id IS NULL";
            try (PreparedStatement bookStmt = connection.prepareStatement(bookSlotSql)) {
                bookStmt.setLong(1, patient.getId());
                bookStmt.setLong(2, slotId);
                int affectedRows = bookStmt.executeUpdate();
                if (affectedRows == 0) {
                    response.setDescription("Слот уже занят или не найден");
                    throw new SlotIsBusyException("Слот уже занят или не найден");
                } else {
                    response.setDescription("Успешно");
                    response.setIsSlotBooked(true);
                }
            }

            // Фиксация транзакции
            connection.commit();
        } catch (Exception e) {
            try {
                if (connection != null && savepoint != null) {
                    response.setDescription("Ошибка при бронировании слота");
                    // Откат до точки сохранения
                    connection.rollback(savepoint);
                } else if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception rollbackEx) {
                log.error("Ошибка при откате транзакции: ", rollbackEx);
                throw new TransactionRollbackException("Ошибка при откате транзакции", rollbackEx);
            }
            log.error("Ошибка при бронировании слота: ", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (Exception closeEx) {
                log.error("Ошибка при закрытии соединения", closeEx);
            }
        }
        return response;
    }
}
