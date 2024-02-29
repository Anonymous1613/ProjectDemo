package com.example.projectdemo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeActivity_1 extends AppCompatActivity {

    Button available,request,Return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee1);



        available = findViewById(R.id.btn_available);
        request = findViewById(R.id.btn_request);
        Return = findViewById(R.id.btn_return);

        available.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AvailabilityActivity_1.class);
            startActivity(intent);
            finish();
        });


    }
}