package com.example.johnyuayan_comp304lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private TestRepository testRepository;
    private LiveData<Boolean> boolResult;
    private LiveData<List<Test>> testList;


    public TestViewModel(@NonNull Application application) {
        super(application);
        testRepository = new TestRepository(application);
        boolResult = testRepository.getBoolResult();
        testList = testRepository.getAllTests();
    }

    public LiveData<Boolean> getBoolResult() {
        return boolResult;
    }
    public LiveData<List<Test>> getTestList() {
        return testList;
    }

    public void insert(Test test) {
        testRepository.insert(test);
    }
    public void setTestList(int patientId) {
        testRepository.setAllTests(patientId);
    }

}
