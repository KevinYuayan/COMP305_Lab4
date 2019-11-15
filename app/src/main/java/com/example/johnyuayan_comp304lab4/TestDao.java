package com.example.johnyuayan_comp304lab4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Data Access Object for the Test class
// Provides SQL CRUD and queries
@Dao
public interface TestDao {
    @Insert
    public void insert(Test test);
    @Query("SELECT * FROM Test WHERE patientId == :patientId")
    public LiveData<List<Test>> getTestList(int patientId);
}
