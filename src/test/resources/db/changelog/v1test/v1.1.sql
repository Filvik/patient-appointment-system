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

