package com.example.projectdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
     String mParam1;
     String mParam2;
    private FirebaseFirestore db;
    Activity view;
    Context FeedbackFragment;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            db = FirebaseFirestore.getInstance();

            EditText editTextFeedback = view.findViewById(R.id.Feedback);
            Button buttonSubmitFeedback = view.findViewById(R.id.btn_Feedback);


            // Set click listener for submit button
            buttonSubmitFeedback.setOnClickListener(v -> {
                // Retrieve feedback text
                String feedback = editTextFeedback.getText().toString().trim();

                // Check if feedback is not empty
                if (!feedback.isEmpty()) {

                    db.collection("Feedback").add(new Feedback(feedback))
                            .addOnSuccessListener(documentReference -> {
                                editTextFeedback.setText("");
                            })
                            .addOnFailureListener(e -> {
                                // Error adding feedback to Firestore
                                // Handle the error (e.g., show an error message)
                            });
                } else {
                    Toast.makeText(FeedbackFragment, "Error Feedback not sent", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }
}