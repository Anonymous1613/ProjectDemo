package com.example.projectdemo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CheckStatusActivity extends AppCompatActivity {

    TextView textissued;
    TextView textnotissued;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Nav_drawerFragment());
        fragmentTransaction.commit();

        textissued = findViewById(R.id.textissued);
        textnotissued = findViewById(R.id.textnonissued);
        db = FirebaseFirestore.getInstance();

        db.collection("Bicycles")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int issuedCount = 0;
                        int notIssuedCount = 0;
                        for (DocumentSnapshot document : task.getResult()) {

                            boolean issued = document.getBoolean("issued");
                            if (issued) {
                                issuedCount++;
                            } else {
                                notIssuedCount++;
                            }
                        }
                        textissued.setText("Number of Bicycles Issued: " + issuedCount);
                        textnotissued.setText("Number of Bicycles Not Issued: " + notIssuedCount);
                    } else {

                        Log.w(TAG, "Error in Fetching Data", task.getException());
                    }
                });

    }
}