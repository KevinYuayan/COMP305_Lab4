package com.example.johnyuayan_comp304lab4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

// Data Access Object for the Patient class
// Provides SQL CRUD and queries
@Dao
public interface PatientDao {
    @Insert
    public void insert(Patient patient);
    @Update
    public void update(Patient patient);
    @Query("SELECT * FROM Patient WHERE patientId == :patientId")
    public LiveData<Patient> getPatient(int patientId);
}
