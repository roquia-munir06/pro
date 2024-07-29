package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hiskytech.feastfleet.databinding.ActivitySigninBinding;

public class ActivitySignin extends AppCompatActivity {
    private ActivitySigninBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        binding.textViewAccount.setOnClickListener(v -> {
            startActivity(new Intent(ActivitySignin.this, ActivitySignup.class));
        });

        binding.btnLogin.setOnClickListener(v -> {
            String loginEmail = binding.editTextTextEmailAddress.getText().toString();
            String loginPassword = binding.editTextTextPassword.getText().toString();

            if (loginEmail.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(ActivitySignin.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Query FireStore for the user document
            db.collection("users")
                    .whereEqualTo("email", loginEmail)
                    .limit(1)
                    .get()
                    .addOnSuccessListener(documents -> {
                        if (documents.isEmpty()) {
                            Toast.makeText(ActivitySignin.this, "No user found with this email", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (com.google.firebase.firestore.QueryDocumentSnapshot document : documents) {
                            com.google.firebase.firestore.DocumentSnapshot doc = documents.getDocuments().get(0);
                            String storedPassword = doc.getString("password") != null ? doc.getString("password").trim() : null;
                            if (storedPassword != null && storedPassword.equals(loginPassword)) {
                                String userName = doc.getString("name");
                                String userId = doc.getString("userId");
                                Toast.makeText(ActivitySignin.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivitySignin.this, MainActivity.class);
                                intent.putExtra("userId", doc.getId());
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ActivitySignin.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(ActivitySignin.this, "Error: " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
