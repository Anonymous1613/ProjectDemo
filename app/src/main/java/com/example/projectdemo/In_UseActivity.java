package com.example.projectdemo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class In_UseActivity extends AppCompatActivity  {

    TextView textView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_use);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Nav_drawerFragment());
        fragmentTransaction.commit();

        db = FirebaseFirestore.getInstance();
        textView = findViewById(R.id.textinuse);

        db.collection("BiCycles")
                .whereEqualTo("allocated", true) // Bicycles allocated to users
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = 0;
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            count = querySnapshot.size();
                        }
                        textView.setText("Number of Bicycles are Currently Allocated: " + count);
                    } else {
                        Log.w(TAG, "Error in Fetching Data", task.getException());
                    }
                });

    }
}