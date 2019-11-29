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

import com.example.johnyuayan_comp304lab4.Patient;
import com.example.johnyuayan_comp304lab4.PatientViewModel;
import com.example.johnyuayan_comp304lab4.R;
import com.example.johnyuayan_comp304lab4.Test;
import com.example.johnyuayan_comp304lab4.TestViewModel;

public class TestActivity extends AppCompatActivity {

    EditText txtTestId;
    EditText txtPatientId;
    EditText txtBloodType;
    EditText txtBPL;
    EditText txtBPH;
    EditText txtTemperature;
    Button btnCreateTest;


    private SharedPreferences myPreference;
    private SharedPreferences.Editor prefEditor;

    private Test test;
    private Patient activePatient;

    private TestViewModel testViewModel;
    private PatientViewModel patientViewModel;
    private int nurseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // Create the SharedPreference
        myPreference = getSharedPreferences("Login", 0);
        //prepare it for edit by creating and Edit object
        prefEditor = myPreference.edit();

        nurseId = myPreference.getInt("id", -1);

        if(nurseId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
        }


        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);

        txtTestId = findViewById(R.id.TestIDtext);
        txtPatientId = findViewById(R.id.PaitentIDText);
        txtBloodType = findViewById(R.id.BloodTypeText);
        txtBPL = findViewById(R.id.BPLText);
        txtBPH = findViewById(R.id.BPHText);
        txtTemperature = findViewById(R.id.TemperatureText);
        btnCreateTest = findViewById(R.id.SaveTestBtn);

        btnCreateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    test = new Test();
                    String testId = txtTestId.getText().toString();
                    String patientId = txtPatientId.getText().toString();
                    String bloodType = txtBloodType.getText().toString();
                    String BPL = txtBPL.getText().toString();
                    String BPH = txtBPH.getText().toString();
                    String temperature = txtTemperature.getText().toString();

                    test.setNurseId(TestActivity.this.nurseId);
                    if(testId.length() > 0) {
                        test.setPatientId(Integer.parseInt(testId));
                    }

                    if(bloodType.length() > 0) {
                        test.setBloodType(bloodType);
                    }

                    if(BPL.length() > 0) {
                        test.setBPL(BPL);
                    }
                    if(BPH.length() > 0) {
                        test.setBPH(BPH);
                    }
                    if(temperature.length() > 0) {
                        try {
                            test.setTemperature(Float.parseFloat(temperature));
                        }catch (Exception e) {
                            txtTemperature.requestFocus();
                            txtTemperature.setError("Invalid Temperature");
                        }
                    }

                    // Called last so we can check db if patient exists
                    if(patientId.length() == 0) {
                        txtPatientId.requestFocus();
                        txtPatientId.setError("Required Field");
                        return;
                    }
                    else {
                        patientViewModel.setPatient(Integer.parseInt(patientId));
                    }
                } catch (Exception e) {
                    Toast.makeText(TestActivity.this, "Invalid form values", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        // observers
        patientViewModel.getActivePatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                activePatient = patient;
            }
        });

        patientViewModel.getIntResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer result) {
                test.setPatientId(activePatient.getPatientId());
                testViewModel.insert(test);
            }
        });

        testViewModel.getBoolResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(TestActivity.this, "Test successfully saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestActivity.this, "Error saving test", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}