package com.example.johnyuayan_comp304lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PatientViewModel extends AndroidViewModel {
    private PatientRepository patientRepository;
    private LiveData<Boolean> boolResult;
    private LiveData<Patient> activePatient;


    public PatientViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        boolResult = patientRepository.getBoolResult();
        activePatient = patientRepository.getActivePatient();
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

    public LiveData<Patient> getActivePatient() {
        return activePatient;
    }
    public LiveData<Boolean> getBoolResult() {
        return boolResult;
    }
}
