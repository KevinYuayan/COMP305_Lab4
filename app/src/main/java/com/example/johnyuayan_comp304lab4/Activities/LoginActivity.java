package com.example.johnyuayan_comp304lab4.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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

        //TODO: uncomment when UI is added
        //txtUser = findViewById(R.id.);
        //txtPassword = findViewById(R.id.);
        //btnLogin = findViewById(R.id.);

        nurseViewModel = ViewModelProviders.of(this).get(NurseViewModel.class);

        // Create the SharedPreference
        myPreference = getSharedPreferences("Login", 0);
        //prepare it for edit by creating and Edit object
        prefEditor = myPreference.edit();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUser.getText().toString();
                String password = txtUser.getText().toString();

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
                Nurse nurse = nurseViewModel.Login(user, password);
                if(nurse == null) {
                    Toast.makeText(LoginActivity.this, "Invalid UserId or Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                prefEditor.putInt("id",nurse.getNurseId());
            }
        });
    }
}
