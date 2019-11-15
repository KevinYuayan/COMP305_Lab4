package com.example.johnyuayan_comp304lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TestViewModel extends AndroidViewModel {
    private TestRepository testRepository;
    private LiveData<Boolean> boolResult;


    public TestViewModel(@NonNull Application application) {
        super(application);
        testRepository = new TestRepository(application);
        boolResult = testRepository.getBoolResult();
    }

    public void insert(Test test) {
        testRepository.insert(test);
    }
}
