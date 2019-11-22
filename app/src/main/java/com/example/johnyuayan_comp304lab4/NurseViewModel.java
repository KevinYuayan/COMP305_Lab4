package com.example.johnyuayan_comp304lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NurseViewModel extends AndroidViewModel {
    private NurseRepository nurseRepository;
    private LiveData<Boolean> boolResult;


    public NurseViewModel(@NonNull Application application) {
        super(application);
        nurseRepository = new NurseRepository(application);
        boolResult = nurseRepository.getBoolResult();
    }

    public LiveData<Boolean> getBoolResult() {
        return boolResult;
    }

    public Nurse getLoginNurse() {
        return nurseRepository.getLoginNurse();
    }

    public void insert(Nurse nurse) {
        nurseRepository.insert(nurse);
    }

    public void Login(String user, String password) {
        nurseRepository.Login(user, password);
    }
}
