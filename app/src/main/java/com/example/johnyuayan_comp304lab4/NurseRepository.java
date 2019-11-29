package com.example.johnyuayan_comp304lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// Manages data sources. Interacts with the Database for the Test Entity/table
public class NurseRepository {
    private final NurseDao nurseDao;
    private MutableLiveData<Boolean> boolResult = new MutableLiveData<>();
    private Nurse loginNurse;

    public NurseRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        nurseDao = db.nurseDao();
    }

    public LiveData<Boolean> getBoolResult() { return boolResult; }

    // public methods for db operations
    public void insert(Nurse nurse) {
        insertAsync(nurse);
    }

    public void Login(String user, String password) {
        LoginAsync(Integer.parseInt(user), password);
    }

    public Nurse getLoginNurse() {
        return loginNurse;
    }


    // Asynchronous private methods for db operations
    private void insertAsync(final Nurse nurse) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nurseDao.Insert(nurse);
                } catch (Exception e) {
                }
            }
        }).start();
    }
    private void LoginAsync(final int user, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loginNurse = nurseDao.Login(user, password);
                    if(loginNurse == null) {
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
