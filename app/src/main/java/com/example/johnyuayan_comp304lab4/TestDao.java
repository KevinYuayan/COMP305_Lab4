package com.example.johnyuayan_comp304lab4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

// Data Access Object for the Test class
// Provides SQL CRUD and queries
@Dao
public interface TestDao {
    @Insert
    public void insert(Test test);
    @Update
    public void update(Test test);
    @Delete
    public void delete(Test test);
}
