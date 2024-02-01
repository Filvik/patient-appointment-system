ALTER TABLE doctors ADD CONSTRAINT specialization_check CHECK (
    specialization IN (
        'CARDIOLOGIST',
        'DERMATOLOGIST',
        'ENDOCRINOLOGIST',
        'GASTROENTEROLOGIST',
        'NEUROLOGIST',
        'ONCOLOGIST',
        'OPHTHALMOLOGIST',
        'ORTHOPEDIST',
        'PEDIATRICIAN',
        'PSYCHIATRIST',
        'PULMONOLOGIST',
        'RADIOLOGIST',
        'UROLOGIST'
    )
);

CREATE INDEX idx_doctors_uuid ON doctors(uuid);
CREATE INDEX idx_patients_uuid ON patients(uuid);

