package com.example.johnyuayan_comp304lab4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

// Data Access Object for the Patient class
// Provides SQL CRUD and queries
@Dao
public interface PatientDao {
    @Insert
    public void insertPatient(Patient patient);
    @Update
    public void updatePatient(Patient patient);
    @Delete
    public void deletePatient(Patient patient);
}
