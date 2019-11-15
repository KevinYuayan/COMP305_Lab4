package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.room.Room;

// Singleton class used to give access to one instance of the TestDatabase
public class TestDatabaseAccessor {
    private static TestDatabase TestDatabaseInstance;
    private static final String TEST_DB_NAME = "test_db";

    private TestDatabaseAccessor(){}

    public static TestDatabase getInstance(Context context) {
        if(TestDatabaseInstance == null) {
            TestDatabaseInstance = Room.databaseBuilder(context, TestDatabase.class, TEST_DB_NAME).build();
        }
        return TestDatabaseInstance;
    }
}
