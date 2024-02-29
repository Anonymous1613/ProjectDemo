package com.example.projectdemo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AvailabilityActivity_1 extends AppCompatActivity  {

    TextView textView;
    Button notify,request;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Nav_drawerFragment());
        fragmentTransaction.commit();

        textView = findViewById(R.id.textavailable);
        notify = findViewById(R.id.btn_notify);
        request = findViewById(R.id.btn_request1);
        db = FirebaseFirestore.getInstance();

        db.collection("BiCycles")
                .whereEqualTo("allocated", false) // Bicycles not allocated to users
                .whereEqualTo("damaged", false)  // Bicycles not marked as damaged
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            count++;
                            if(count==0){
                                Toast.makeText(this, "Click on Notify", Toast.LENGTH_SHORT).show();
                            }
                        }
                        textView.setText("Number of Bi-Cycles Available : " + count);
                    }else {
                        Log.w(TAG, "Error in Fetching Data", task.getException());
                    }
                });


    }
}