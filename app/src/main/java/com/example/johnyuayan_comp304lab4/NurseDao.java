package com.example.johnyuayan_comp304lab4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

// Data Access Object for the Nurse class
// Provides SQL CRUD and queries
@Dao
public interface NurseDao {
    @Insert
    public void insertNurse(Nurse nurse);
    @Update
    public void updateNurse(Nurse nurse);
    @Delete
    public void deleteNurse(Nurse nurse);
}
