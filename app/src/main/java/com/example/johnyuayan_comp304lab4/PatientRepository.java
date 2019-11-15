package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// Manages data sources. Interacts with the Database for the Test Entity/table
public class PatientRepository {
    private final PatientDao patientDao;
    private MutableLiveData<Boolean> boolResult = new MutableLiveData<>();

    public PatientRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        patientDao = db.patientDao();
    }
    public LiveData<Boolean> getBoolResult() { return boolResult; }

    // public methods for db operations
    public void insert(Patient patient) {
        insertAsync(patient);
    }

    // Asynchronous private methods for db operations
    private void insertAsync(final Patient patient) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    patientDao.insert(patient);
                    boolResult.postValue(true);
                } catch (Exception e) {
                    boolResult.postValue(false);
                }
            }
        }).start();
    }

}
