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

    public void insert(Nurse nurse) {
        nurseRepository.insert(nurse);
    }

    public Nurse Login(String user, String password) {
        return nurseRepository.Login(user, password);
    }
}
