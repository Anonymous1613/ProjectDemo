package com.example.projectdemo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class SecurityActivity_1 extends AppCompatActivity  {

    Button available,status,add,uploadlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Nav_drawerFragment());
        fragmentTransaction.commit();

        available = findViewById(R.id.btn_available);
        status = findViewById(R.id.btn_status);
        add = findViewById(R.id.btn_add);
        uploadlist = findViewById(R.id.btn_uploadList);


        available.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AvailabilityActivity_1.class);
            startActivity(intent);
            finish();
        });

        status.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CheckStatusActivity.class);
            startActivity(intent);
            finish();
        });

        add.setOnClickListener(v -> {
            AddBiCycleDialogFragment dialogFragment = new AddBiCycleDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AddBicycleDialogFragment");
        });
    }
}