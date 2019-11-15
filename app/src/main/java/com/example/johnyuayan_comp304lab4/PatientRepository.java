package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// Manages data sources. Interacts with the Database for the Test Entity/table
public class PatientRepository {
    private final PatientDao patientDao;
    private MutableLiveData<Boolean> boolResult = new MutableLiveData<>();
    private LiveData<Patient> activePatient;

    public PatientRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        patientDao = db.patientDao();
    }
    // Getters
    public LiveData<Boolean> getBoolResult() { return boolResult; }
    public LiveData<Patient> getActivePatient() {
        return activePatient;
    }

    // public methods for db operations
    public void insert(Patient patient) {
        insertAsync(patient);
    }
    public void update(Patient patient) {
        updateAsync(patient);
    }
    public void setPatient(int patientId) {
        setPatientAsync(patientId);
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
    private void updateAsync(final Patient patient) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(patientDao.getPatient(patient.getPatientId()) == null) {
                        throw new Exception();
                    }
                    patientDao.update(patient);
                    activePatient = patientDao.getPatient(patient.getPatientId());
                    boolResult.postValue(true);
                } catch (Exception e) {
                    boolResult.postValue(false);
                }
            }
        }).start();
    }

    private void setPatientAsync(final int patientId) {
        new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                activePatient = patientDao.getPatient(patientId);
                boolResult.postValue(true);
            } catch (Exception e) {
                boolResult.postValue(false);
            }
        }
    }).start();
    }
}
