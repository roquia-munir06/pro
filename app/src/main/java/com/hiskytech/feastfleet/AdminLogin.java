package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLogin extends AppCompatActivity {

    private static final String TAG = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        AppCompatButton btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.editTextTextEmailAddress)).getText().toString();
                String password = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Admin")
                        .whereEqualTo("email", email) // Check for email equality
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        String storedPassword = documentSnapshot.getString("password");
                                        if (storedPassword != null && storedPassword.equals(password)) {
                                            Intent intent = new Intent(AdminLogin.this, AdminHome.class);
                                            startActivity(intent);
                                            return; // Exit loop if login successful
                                        }
                                    }
                                    Toast.makeText(AdminLogin.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AdminLogin.this, "No user found with this email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminLogin.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
