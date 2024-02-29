package com.example.projectdemo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class RegisterActivity_1 extends AppCompatActivity {

    private TextInputEditText editTextEmail;
    TextInputEditText editTextEmployeeid;
    TextInputEditText editTextRole;
    private TextInputEditText editTextPassword;
    Button buttonreg;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private ProgressBar progressBar;
    TextView textView;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        FirebaseApp.initializeApp(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressbar);
        buttonreg = findViewById(R.id.register);
        textView = findViewById(R.id.loginow);
        editTextEmployeeid = findViewById(R.id.emp_id);
        editTextRole = findViewById(R.id.role);

        textView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getApplicationContext(), LoginActivity_1.class);
            startActivity(intent);
            finish();
        });

        buttonreg.setOnClickListener(v -> RegisterActivity_1.this.registerUser());

    }

    private void registerUser() {

        String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
        String role = editTextRole.toString().trim();
        int emp_id = editTextEmployeeid.getId();

        if (TextUtils.isEmpty((String.valueOf(emp_id)))) {
            Toast.makeText(this, "Please enter employee id ", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(role)) {
            Toast.makeText(this, "Please enter role", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("Admins")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d(TAG, "Admin registration successful");
                                            Toast.makeText(RegisterActivity_1.this, "Admin registration successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.w(TAG, "Admin registration failed", task1.getException());

                                            showRegistrationErrorDialog("Admin registration failed. Please try again later.");
                                        }
                                    });
                        } else {
                            db.collection("Employees")
                                    .whereEqualTo("email", email)
                                    .get()
                                    .addOnCompleteListener(task12 -> {
                                        if (task12.isSuccessful()) {
                                            if (!task12.getResult().isEmpty()) {
                                                mAuth.createUserWithEmailAndPassword(email, password)
                                                        .addOnCompleteListener(task121 -> {
                                                            if (task121.isSuccessful()) {
                                                                Log.d(TAG, "Employee registration successful");
                                                                Toast.makeText(RegisterActivity_1.this, "Employee registration successful", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Log.w(TAG, "Employee registration failed", task121.getException());
                                                                showRegistrationErrorDialog("Employee registration failed. Please try again later.");
                                                            }
                                                        });
                                            } else {
                                                db.collection("Security")
                                                        .whereEqualTo("email", email)
                                                        .get()
                                                        .addOnCompleteListener(task1212 -> {
                                                            if (task1212.isSuccessful()) {
                                                                if (!task1212.getResult().isEmpty()) {
                                                                    mAuth.createUserWithEmailAndPassword(email, password)
                                                                            .addOnCompleteListener(task12121 -> {
                                                                                if (task12121.isSuccessful()) {
                                                                                    Log.d(TAG, "Security registration successful");
                                                                                    Toast.makeText(RegisterActivity_1.this, "Security registration successful", Toast.LENGTH_SHORT).show();
                                                                                } else {
                                                                                    Log.w(TAG, "Security registration failed", task12121.getException());
                                                                                    showRegistrationErrorDialog("Security registration failed. Please try again later.");
                                                                                }
                                                                            });
                                                                } else {
                                                                    showRegistrationErrorDialog("User details not found. Registration failed.");
                                                                }
                                                            } else {
                                                                Log.w(TAG, "Error getting user details from Firestore for security", task1212.getException());
                                                                showRegistrationErrorDialog("An error occurred while checking user details. Please try again later.");
                                                            }
                                                        });
                                            }
                                        } else {
                                            Log.w(TAG, "Error getting user details from Firestore for employees", task12.getException());
                                            showRegistrationErrorDialog("An error occurred while checking user details. Please try again later.");
                                        }
                                    });
                        }
                    } else {
                        Log.w(TAG, "Error getting user details from Firestore for admins", task.getException());
                        showRegistrationErrorDialog("An error occurred while checking user details. Please try again later.");
                    }
                });
    }

    private void showRegistrationErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity_1.this);
        builder.setTitle("Registration Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}