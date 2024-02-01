DELETE FROM time_slots;
DELETE FROM patients;
DELETE FROM doctors;

-- Создаем тестового врача с ID 101 и уникальным UUID
INSERT INTO doctors (id, uuid, full_name, gender, specialization, created_at, updated_at)
VALUES (101, '5517f51a-ad5f-41d7-870f-450772937337', 'Доктор Иванова', 'FEMALE', 'DERMATOLOGIST', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Создаем тестового врача с ID 105 и уникальным UUID
INSERT INTO doctors (id, uuid, full_name, gender, specialization, created_at, updated_at)
VALUES (105, '5517f51a-ad5f-41d7-870f-450772937330', 'Доктор Иванов', 'MALE', 'DERMATOLOGIST', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Создаем тестового пациента с уникальным UUID
INSERT INTO patients (id, uuid, full_name, gender, date_of_birth, insurance_policy_number, phone_number, created_at, updated_at)
VALUES (1, '5517f51a-ad5f-41d7-870f-450772937437', 'Пациент Петров', 'MALE', '1980-01-01', 123456788, '123-456-7890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Вставляем тестовые слоты:
-- Свободный слот 1
INSERT INTO time_slots (id, doctor_id, patient_id, start_time, end_time, date, created_at, updated_at)
VALUES (6, 101, NULL, '09:00', '10:00', '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Свободный слот 2
INSERT INTO time_slots (id, doctor_id, patient_id, start_time, end_time, date, created_at, updated_at)
VALUES (7, 101, NULL, '10:00', '11:00', '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Свободный слот 3
INSERT INTO time_slots (id, doctor_id, patient_id, start_time, end_time, date, created_at, updated_at)
VALUES (8, 105, NULL, '19:00', '20:00', '2024-01-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Свободный слот 4
INSERT INTO time_slots (id, doctor_id, patient_id, start_time, end_time, date, created_at, updated_at)
VALUES (9, 105, NULL, '15:00', '15:40', '2024-01-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Свободный слот 5
INSERT INTO time_slots (id, doctor_id, patient_id, start_time, end_time, date, created_at, updated_at)
VALUES (11, 101, NULL, '15:00', '15:20', '2024-01-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Забронированный слот
INSERT INTO time_slots (id, doctor_id, patient_id, start_time, end_time, date, created_at, updated_at)
VALUES (10, 101, 1, '11:00', '12:00', '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



