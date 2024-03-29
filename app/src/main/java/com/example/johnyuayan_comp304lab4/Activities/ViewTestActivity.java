package com.example.johnyuayan_comp304lab4.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johnyuayan_comp304lab4.R;
import com.example.johnyuayan_comp304lab4.Test;
import com.example.johnyuayan_comp304lab4.TestViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewTestActivity extends AppCompatActivity {
    EditText txtPatientId;
    Button btnTestList;
    ListView lstTestDisplay;

    private SharedPreferences myPreference;
    private SharedPreferences.Editor prefEditor;

    private TestViewModel testViewModel;
    private int nurseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        // Create the SharedPreference
        myPreference = getSharedPreferences("Login", 0);
        //prepare it for edit by creating and Edit object
        prefEditor = myPreference.edit();

        nurseId = myPreference.getInt("id", -1);

        if(nurseId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);

        lstTestDisplay = findViewById(R.id.lstTest);
        txtPatientId = findViewById(R.id.searchPatientIdtext);
        btnTestList = findViewById(R.id.searchbtn);


        btnTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientId = txtPatientId.getText().toString();

                if(patientId.length() > 0) {
                    testViewModel.setTestList(Integer.parseInt(patientId));
                }
            }
        });

        testViewModel.getTestList().observe(this, new Observer<List<Test>>() {
            @Override
            public void onChanged(List<Test> tests) {
                List<String> testStringList = new ArrayList<>();
                for (Test test: tests) {
                    testStringList.add(test.toString());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, testStringList);
                lstTestDisplay.setAdapter(arrayAdapter);
            }
        });

        testViewModel.getBoolResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(ViewTestActivity.this, "Tests successfully found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewTestActivity.this, "Error finding tests", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
