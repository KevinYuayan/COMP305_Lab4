package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

// Manages data sources. Interacts with the Database for the Test Entity/table
public class PatientRepository {
    private final PatientDao patientDao;
    // -1 = error
    // 1 = insert success
    // 2 = update success
    // 3 = getPatient success
    private MutableLiveData<Integer> intResult = new MutableLiveData<>();
    private LiveData<Patient> activePatient;


    private LiveData<List<Patient>> patients;

    public PatientRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        patientDao = db.patientDao();
        patients = patientDao.getPatientList();
    }
    // Getters
    public LiveData<List<Patient>> getPatients() {
        return patients;
    }
    public LiveData<Integer> getIntResult() { return intResult; }
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
                    activePatient = patientDao.getPatient(patient.getPatientId());
                    intResult.postValue(1);
                } catch (Exception e) {
                    intResult.postValue(-1);
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
                    intResult.postValue(2);
                } catch (Exception e) {
                    intResult.postValue(-1);
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
                if(activePatient == null) {
                    throw new Exception();
                }
                intResult.postValue(3);
            } catch (Exception e) {
                intResult.postValue(-2);
            }
        }
    }).start();
    }
}
