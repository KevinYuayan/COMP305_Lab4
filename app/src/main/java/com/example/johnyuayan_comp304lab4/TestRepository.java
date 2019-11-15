package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

// Manages data sources. Interacts with the Database for the Test Entity/table
public class TestRepository {
    private final TestDao testDao;
    private LiveData<List<Test>> AllTests;
    private MutableLiveData<Boolean> boolResult = new MutableLiveData<>();

    public TestRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        testDao = db.testDao();
    }
    // Getters
    public LiveData<Boolean> getBoolResult() { return boolResult; }
    public LiveData<List<Test>> getAllTests() {
        return AllTests;
    }

    // public methods for db operations
    public void insert(Test test) {
        insertAsync(test);
    }
    public void setAllTests(int patientId) {
        setAllTestsAsync(patientId);
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
    private void setAllTestsAsync(final int patientId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AllTests = testDao.getTestList(patientId);
                    if(AllTests == null) {
                        throw new Exception();
                    }
                    boolResult.postValue(true);
                } catch (Exception e) {
                    boolResult.postValue(false);
                }
            }
        }).start();
    }

}
