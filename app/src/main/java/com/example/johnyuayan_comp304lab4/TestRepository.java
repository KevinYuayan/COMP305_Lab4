package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// Manages data sources. Interacts with the Database for the Test Entity/table
public class TestRepository {
    private final TestDao testDao;
    private MutableLiveData<Boolean> boolResult = new MutableLiveData<>();

    public TestRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        testDao = db.testDao();
    }
    public LiveData<Boolean> getBoolResult() { return boolResult; }

    // public methods for db operations
    public void insert(Test test) {
        insertAsync(test);
    }

    // Asynchronous private methods for db operations
    private void insertAsync(final Test test) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testDao.insert(test);
                    boolResult.postValue(true);
                } catch (Exception e) {
                    boolResult.postValue(false);
                }
            }
        }).start();
    }

}
