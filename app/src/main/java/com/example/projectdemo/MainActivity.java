package com.example.projectdemo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    List<Admin> Admins;
    List<Employee> Employees;
    List<Security> Security;
    List<BiCycles> BiCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Nav_drawerFragment());
        fragmentTransaction.commit();

        Admins = new ArrayList<>();
        Employees = new ArrayList<>();
        Security = new ArrayList<>();
        BiCycle = new ArrayList<>();

        Admins = new ArrayList<>();
        Admins.add(new Admin("Remo","Remo123@gmail.com", "Admin",12345));
        Admins.add(new Admin("Rupa", "Rupa456@gmail.com","Admin",78945));

        Employees = new ArrayList<>();
        Employees.add(new Employee("Kishore", "Kishore123@gmail.com","Employee", 13425));
        Employees.add(new Employee("Krish", "Krish456@gmail.com","Employee" ,15230));

        Security = new ArrayList<>();
        Security.add(new Security("Gopal", "Gopal123@gmail.com","Security" ,45862));
        Security.add(new Security("Rolex", "Rolex452@gmail.com","Security" ,48625));

        BiCycle = new ArrayList<>();
        BiCycle.add(new BiCycles("Red",  25809));
        BiCycle.add(new BiCycles("Blue",48620));

        for (Admin admin : Admins) {
            db.collection("Admins")
                    .add(admin)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "Admin added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding admin", e));
    }
        for (Employee employee : Employees) {
            db.collection("Employees")
                    .add(employee)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "Employee added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding employee", e));
        }
        for (Security security : Security) {
            db.collection("Security")
                    .add(security)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "Security added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding security", e));
        }

        for (BiCycles bicycle : BiCycle) {
            db.collection("BiCycle")
                    .add(bicycle)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "BiCycle added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding BiCycle", e));
        }

}
}