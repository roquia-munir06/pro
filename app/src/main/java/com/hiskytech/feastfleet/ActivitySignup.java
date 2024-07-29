package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hiskytech.feastfleet.databinding.ActivitySignupBinding;

public class ActivitySignup extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        binding.textViewAccount.setOnClickListener(v -> {
            startActivity(new Intent(ActivitySignup.this, ActivitySignin.class));
        });

        binding.btnSignUp.setOnClickListener(v -> {
            String userName = binding.etName.getText().toString();
            String userEmail = binding.editTextTextEmailAddress.getText().toString();
            String password = binding.editTextTextPassword.getText().toString();

            if (userName.isEmpty() || userEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(ActivitySignup.this, "Fill All fields", Toast.LENGTH_SHORT).show();
                return;
            }

            ModelUser user = new ModelUser();
            user.setName(userName);
            user.setEmail(userEmail);
            user.setPassword(password);

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        String userId = documentReference.getId();
                        user.setUserId(userId);

                        db.collection("users").document(documentReference.getId()).set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ActivitySignup.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ActivitySignup.this, ActivitySignin.class));
                                    Toast.makeText(ActivitySignup.this, userId, Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ActivitySignup.this, "Error is: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
