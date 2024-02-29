package com.example.projectdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBiCycleDialogFragment extends DialogFragment {


    FirebaseFirestore db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Bicycle");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_bi_cycle, null);
        builder.setView(view);

        EditText editTextBicycleId = view.findViewById(R.id.editTextBicycleId);
        EditText editTextBicycleColor = view.findViewById(R.id.editTextBicycleColor);

        builder.setPositiveButton("Add", (dialog, which) -> {

            String bicycleId = editTextBicycleId.getText().toString().trim();
            String bicycleColor = editTextBicycleColor.getText().toString().trim();


            addBicycleToFirestore(bicycleId, bicycleColor);
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    private void addBicycleToFirestore(String bicycleId, String bicycleColor) {

        Map<String, Object> bicycle = new HashMap<>();
        bicycle.put("id", bicycleId);
        bicycle.put("color", bicycleColor);


        db.collection("Bicycles")
                .document(bicycleId)
                .set(bicycle)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Bicycle added successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to add bicycle: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });


    }
}
