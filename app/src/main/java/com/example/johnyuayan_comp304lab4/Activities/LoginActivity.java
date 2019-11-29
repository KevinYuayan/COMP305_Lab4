package com.example.johnyuayan_comp304lab4.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johnyuayan_comp304lab4.Nurse;
import com.example.johnyuayan_comp304lab4.NurseViewModel;
import com.example.johnyuayan_comp304lab4.R;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser;
    EditText txtPassword;
    Button btnLogin;

    NurseViewModel nurseViewModel;

    private SharedPreferences myPreference;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.UserName);
        txtPassword = findViewById(R.id.UserPassword);
        btnLogin = findViewById(R.id.LoginButton);

        nurseViewModel = ViewModelProviders.of(this).get(NurseViewModel.class);

//        Nurse aNurse = new Nurse();
//        aNurse.setNurseId(100);
//        aNurse.setFirstName("Jane");
//        aNurse.setLastName("Doe");
//        aNurse.setDepartment("ICU");
//        aNurse.setPassword("123");

//        nurseViewModel.insert(aNurse);

        // Create the SharedPreference
        myPreference = getSharedPreferences("Login", 0);
        //prepare it for edit by creating and Edit object
        prefEditor = myPreference.edit();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUser.getText().toString();
                String password = txtPassword.getText().toString();

                if(user.length() == 0) {
                    txtUser.requestFocus();
                    txtUser.setError("Required Field");
                    return;
                }
                if(password.length() == 0) {
                    txtPassword.requestFocus();
                    txtPassword.setError("Required Field");
                    return;
                }
                nurseViewModel.Login(user, password);
            }
        });

        nurseViewModel.getBoolResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    prefEditor.putInt("id", nurseViewModel.getLoginNurse().getNurseId());
                    prefEditor.commit();
                    System.out.println(nurseViewModel.getLoginNurse().getNurseId());
                    System.out.println(myPreference.getInt("id", 0));
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid UserId or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
