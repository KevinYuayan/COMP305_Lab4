package com.example.johnyuayan_comp304lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PatientViewModel extends AndroidViewModel {
    private PatientRepository patientRepository;
    private LiveData<Integer> intResult;
    private LiveData<Patient> activePatient;

    private LiveData<List<Patient>> patients;


    public PatientViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        intResult = patientRepository.getIntResult();
        activePatient = patientRepository.getActivePatient();
        patients = patientRepository.getPatients();
    }
    public void insert(Patient patient) {
        patientRepository.insert(patient);
    }
    public void update(Patient patient) {
        patientRepository.update(patient);
    }
    public void setPatient(int patientId) {
        patientRepository.setPatient(patientId);
    }

    public LiveData<List<Patient>> getPatients() {
        return patients;
    }
    public LiveData<Patient> getActivePatient() {
        return activePatient;
    }
    public LiveData<Integer> getIntResult() {
        return intResult;
    }
}
