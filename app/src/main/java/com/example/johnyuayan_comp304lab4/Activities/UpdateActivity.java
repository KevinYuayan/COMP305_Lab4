package com.example.johnyuayan_comp304lab4.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johnyuayan_comp304lab4.Patient;
import com.example.johnyuayan_comp304lab4.PatientViewModel;
import com.example.johnyuayan_comp304lab4.R;

public class UpdateActivity extends AppCompatActivity {

    EditText txtPatientId;
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtDepartment;
    EditText txtRoomNumber;
    Button btnUpdatePatient;

    private Patient activePatient;
    private SharedPreferences myPreference;
    private SharedPreferences.Editor prefEditor;

    private PatientViewModel patientViewModel;
    private int nurseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Create the SharedPreference
        myPreference = getSharedPreferences("Login", 0);
        //prepare it for edit by creating and Edit object
        prefEditor = myPreference.edit();

        nurseId = myPreference.getInt("id", -1);

        if(nurseId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
        }


        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);

        txtPatientId = findViewById(R.id.PaitentIDText);
        txtFirstName = findViewById(R.id.FirstNameUpdateText);
        txtLastName = findViewById(R.id.LastNameEditText);
        txtDepartment = findViewById(R.id.DepartmentText);
        txtRoomNumber = findViewById(R.id.RoomText);
        btnUpdatePatient = findViewById(R.id.updateButton);


        //Checks database for activePatient with activePatient Id whenever text is changed.
        txtPatientId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) {
                    patientViewModel.setPatient(Integer.parseInt(editable.toString()));
                }
            }
        });



        btnUpdatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    activePatient = new Patient ();
                    String patientId = txtPatientId.getText().toString();
                    String firstName = txtFirstName.getText().toString();
                    String lastName = txtLastName.getText().toString();
                    String department = txtDepartment.getText().toString();
                    String roomNumber = txtRoomNumber.getText().toString();

                    activePatient.setNurseId(nurseId);

                    if(firstName.length() == 0) {
                        txtFirstName.requestFocus();
                        txtFirstName.setError("Required Field");
                        return;
                    }
                    activePatient.setFirstName(firstName);

                    if(lastName.length() == 0) {
                        txtLastName.requestFocus();
                        txtLastName.setError("Required Field");
                        return;
                    }
                    activePatient.setLastName(lastName);

                    if(department.length() == 0) {
                        txtDepartment.requestFocus();
                        txtDepartment.setError("Required Field");
                        return;
                    }
                    activePatient.setDepartment(department);

                    if(roomNumber.length() == 0) {
                        txtRoomNumber.requestFocus();
                        txtRoomNumber.setError("Required Field");
                        return;
                    }
                    activePatient.setRoomNumber(Integer.parseInt(roomNumber));


                    // Called last so we can check db if activePatient exists
                    if(patientId.length() == 0) {
                        txtPatientId.requestFocus();
                        txtPatientId.setError("Required Field");
                        return;
                    }
                    else {
                        patientViewModel.setPatient(Integer.parseInt(patientId));
                    }

                } catch (Exception e) {
                    Toast.makeText(UpdateActivity.this, "Invalid form values", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //observers
        patientViewModel.getActivePatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                activePatient.setPatientId(patient.getPatientId());
                patientViewModel.update(patient);
            }
        });

        patientViewModel.getIntResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer result) {
                if (result == 2) {
                    Toast.makeText(UpdateActivity.this, "Patient successfully updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateActivity.this, "Error updating activePatient", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}