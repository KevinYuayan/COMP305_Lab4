package com.example.johnyuayan_comp304lab4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

// Data Access Object for the Nurse class
// Provides SQL CRUD and queries
@Dao
public interface NurseDao {
    @Insert
    public void Insert(Nurse nurse);
    @Query("SELECT * FROM Nurse WHERE nurseId == :user AND password == :password")
    public Nurse Login(int user, String password);
}
