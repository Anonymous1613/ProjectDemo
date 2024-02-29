package com.example.projectdemo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




import java.util.Objects;

public class LoginActivity_1 extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    RadioButton radioemp,radioadm,radiosec;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        Button buttonLogin = findViewById(R.id.btn_login);
        TextView textView = findViewById(R.id.anchor);
        radioemp = findViewById(R.id.radio_employee);
        radioadm = findViewById(R.id.radio_admin);
        radiosec = findViewById(R.id.radio_security);
        textView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getApplicationContext(), RegisterActivity_1.class);
            startActivity(intent);
            finish();
        });

        buttonLogin.setOnClickListener(v -> {
            loginUser();
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    private void loginUser() {

        String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;

        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        if (radioemp.isChecked()) {
                            // Employee login
                            startActivity(new Intent(LoginActivity_1.this, EmployeeActivity_1.class));
                        } else if (radioadm.isChecked()) {
                            // Admin login
                            startActivity(new Intent(LoginActivity_1.this, AdminActivity_1.class));
                        } else if (radiosec.isChecked()) {
                            // Security login
                            startActivity(new Intent(LoginActivity_1.this, SecurityActivity_1.class));
                        }
                        // Finish login activity
                        finish();
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity_1.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });    }
}