package com.example.projectdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView textViewUsername;
    private TextView textViewEmail;
    private TextView textViewRole;
    private TextView textViewEmployeeId;
    private TextView textViewPhoneNumber;
    private ImageView imageViewProfilePicture;
    Button buttonChangeProfilePicture;
    Button buttonUpdatePhoneNumber;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseStorage storage;

    Uri imageUri;
    View view;


    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        textViewUsername = view.findViewById(R.id.Username);
        textViewEmail = view.findViewById(R.id.Email);
        textViewRole = view.findViewById(R.id.Role);
        textViewEmployeeId = view.findViewById(R.id.EmpId);
        textViewPhoneNumber = view.findViewById(R.id.Contact);
        imageViewProfilePicture = view.findViewById(R.id.ProfilePicture);

        loadUserDetails();
        buttonChangeProfilePicture.setOnClickListener(v -> openImagePicker());
        buttonUpdatePhoneNumber.setOnClickListener(v -> updatePhoneNumber());

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
}

    private void updatePhoneNumber() {

    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void loadUserDetails() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DocumentReference userRef = db.collection("users").document(user.getUid());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String username = document.getString("username");
                        String email = user.getEmail();
                        String role = document.getString("role");
                        String employeeId = document.getString("employeeId");
                        String phoneNumber = user.getPhoneNumber();

                        // Set user details to TextViews
                        textViewUsername.setText(username);
                        textViewEmail.setText(email);
                        textViewRole.setText(role);
                        textViewEmployeeId.setText(employeeId);
                        textViewPhoneNumber.setText(phoneNumber);
                    }
                }
            });
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewProfilePicture.setImageURI(imageUri);
        }
    }
}