<h1> Система записи пациентов на приём </h1>
<a href="https://github.com/YourGitHub/RegistrationSystem">
    <img src="hospital.jpg" width="200" alt ="Hospital Image" height="200">
  </a>
  <br>
 <h2>Описание приложения:</h2>
 <p>Приложение реализует Rest и SOAP API для системы записи пациентов к врачам. Это API может быть использовано медицинскими учреждениями для автоматизации процесса записи на приём к врачам. </p>
  <h3>В приложении реализованы следующие возможности:</h3>
  <ul>
     <li>Получение списка свободных слотов времени к указанному врачу</li>
     <li>Запись пациента на свободный слот времени</li>
     <li>Отображение списка всех записей одного пациента</li>
     <li>Создание расписания приёма врачей через SOAP сервис</li>
  </ul>
  <h3>Для работы приложения необходимо:</h3>
       <li><a href="https://en.wikipedia.org/wiki/PostgreSQL">Database PostgreSQL</a></li>
       <h3>Запуск ПО:</h3>
       <p>Для запуска программы необходимо прописать настройки подключения в файл <b>external.properties</b> который должен находится в корневой папке проекта. В этом файле укажите параметры подключения к базе данных, которая должна быть создана заранее.
       При старте приложение автоматически создаются необходимые таблицы (Врачи, Пациенты, Талоны) для работы системы. Обращаясь к контроллерам по их адресу реализуется выше указанный функционал.</p>
<h3>SOAP сервис для создания расписания приёмов</h3>

В проекте реализован SOAP сервис, предназначенный для автоматизации создания расписания приёмов к врачам. Сервис предоставляет возможность генерации талонов (слотов времени для приема) на основании заданных параметров.

SOAP сервис доступен по следующему адресу WSDL для интеграции и использования: <a href="http://localhost:8090/ScheduleService?wsdl">http://localhost:8090/ScheduleService?wsdl </a>

Пример запроса к SOAP сервису для создания слотов, в рамках заданного периода времени, учитывая день недели:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sch="http://schedule.service.system.appointment.patient.example.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <sch:createTimeSlotsForDifficultWeek>
         <!--Optional:-->
         <arg0>
            <!--Optional:-->
            <doctorId>101</doctorId>
            <!--Optional:-->
            <dateFrom>2024-01-10</dateFrom>
            <!--Optional:-->
            <dateTo>2024-01-20</dateTo>
            <slotDurationInMinutes>30</slotDurationInMinutes>
            <!--Zero or more repetitions:-->
             
            <scheduleRequestOfDay>
               <!--Optional:-->
               <dayOfWeek>MONDAY</dayOfWeek>
               <!--Optional:-->
               <scheduleRequest>
                  <!--Optional:-->
                  <startWorkTime>01:00:00</startWorkTime>
                  <breakForLunchInMinutes>60</breakForLunchInMinutes>
                  <workingTimeInHoursInDay>10</workingTimeInHoursInDay>
               </scheduleRequest>
             </scheduleRequestOfDay>
             
             <scheduleRequestOfDay>
               <!--Optional:-->
               <dayOfWeek>THURSDAY</dayOfWeek>
               <!--Optional:-->
               <scheduleRequest>
                  <!--Optional:-->
                  <startWorkTime>09:00:00</startWorkTime>
                  <breakForLunchInMinutes>60</breakForLunchInMinutes>
                  <workingTimeInHoursInDay>6</workingTimeInHoursInDay>
               </scheduleRequest>
             </scheduleRequestOfDay>
             
             <scheduleRequestOfDay>
               <!--Optional:-->
               <dayOfWeek>WEDNESDAY</dayOfWeek>
               <!--Optional:-->
               <scheduleRequest>
                  <!--Optional:-->
                  <startWorkTime>12:00:00</startWorkTime>
                  <breakForLunchInMinutes>60</breakForLunchInMinutes>
                  <workingTimeInHoursInDay>8</workingTimeInHoursInDay>
               </scheduleRequest>
             </scheduleRequestOfDay>

         </arg0>
      </sch:createTimeSlotsForDifficultWeek>
   </soapenv:Body>
</soapenv:Envelope>
```

<h3>В приложении так же реализовано:</h3>
    <ul>
       <li>Логирование с использованием log4j2</li>
          <p>Настройки логирования находятся в файле <b>log4j2.xml</b>.
          Логи пишутся в файл <b>application.log</b>, который лежит в папке <code>logs</code>.</p>
       <li><a href="https://swagger.io/solutions/api-documentation/">Swagger</a></li>
            <p>Фреймворк Swagger позволяет создавать интерактивную документацию по API. Swagger доступен при запущенном приложении по адресу : <a  href=http://localhost:8080/swagger-ui/index.html#/>http://localhost:8080/swagger-ui/index.html#/ </a>.</p>
       <li>Тестирование</li>
           <p>Тестирование выполняется на тестовой базе данных H2, с использованием тестовых данных, загружаемых из файла <b>data.sql</b> перед началом тестов. Также доступна возможность использовать базу данных PostgreSQL для тестирования. Настройки подключения к базам данных находятся в файле <b>application-test.properties</b>.</p>
       <li>Управление версиями базы данных с использованием <a href="https://www.liquibase.com/">Liquibase</a></li>
           
*При смене БД в тестах необходимом обратить внимание на файл <b>master-chengelog-test.yaml</b></p>
    </ul>

