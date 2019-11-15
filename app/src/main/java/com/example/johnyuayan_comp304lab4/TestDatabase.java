package com.example.johnyuayan_comp304lab4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Abstract class to represent the Database in Room API (
@Database(entities = {Nurse.class, Patient.class, Test.class}, version = 1)
public abstract class TestDatabase extends RoomDatabase {
    public abstract NurseDao nurseDao();
    public abstract TestDao testDao();
    public abstract PatientDao patientDao();
}
