ALTER TABLE time_slots
ADD CONSTRAINT time_slots_doctor_id_date_start_time_unique UNIQUE (doctor_id, date, start_time);
