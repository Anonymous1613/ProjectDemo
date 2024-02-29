package com.example.projectdemo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AdminActivity_1 extends AppCompatActivity {

    private static final int PICK_EXCEL_FILE_REQUEST_CODE = 1001;
    FirebaseStorage storage;
    StorageReference storageRef;

    Button available,usage,damaged,upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);


        available = findViewById(R.id.btn_available);
        usage = findViewById(R.id.btn_usage);
        damaged = findViewById(R.id.btn_damaged);
        upload = findViewById(R.id.btn_upload);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        available.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AvailabilityActivity_1.class);
            startActivity(intent);
            finish();
        });
        usage.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), In_UseActivity.class);
            startActivity(intent);
            finish();
        });

        upload.setOnClickListener(v -> selectExcelFile());

    }

    private void selectExcelFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Excel File"), PICK_EXCEL_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_EXCEL_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                uploadExcelFile(selectedFileUri);
            }
        }
    }

    private void uploadExcelFile(Uri fileUri) {
        String fileName = getFileName(fileUri);
        if (fileName != null) {
            StorageReference fileReference = storageRef.child("excel_files/" + fileName);
            fileReference.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // File uploaded successfully
                        Toast.makeText(this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle file upload failure
                        Toast.makeText(this, "Failed to upload file", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri fileUri) {
        String fileName = null;
        if (Objects.equals(fileUri.getScheme(), "content")) {
            try (Cursor cursor = getContentResolver().query(fileUri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fileName == null) {
            fileName = fileUri.getPath();
            int cut = Objects.requireNonNull(fileName).lastIndexOf('/');
            if (cut != -1) {
                fileName = fileName.substring(cut + 1);
            }
        }
        return fileName;
    }
}