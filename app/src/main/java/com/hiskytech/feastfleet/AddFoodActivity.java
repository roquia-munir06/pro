package com.hiskytech.feastfleet;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        db = FirebaseFirestore.getInstance();

        AppCompatButton btnAddFood = findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(v -> {
            uploadImageAndData();
        });

        TextView imageView = findViewById(R.id.tvAddImage);
        imageView.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ImageView imageView = findViewById(R.id.foodimage);
            imageView.setImageURI(selectedImageUri);
        }
    }

    private void uploadImageAndData() {
        if (selectedImageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("food_images/" + System.currentTimeMillis() + ".jpg");
            storageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Get download URL
                            String imageUrl = uri.toString();
                            // Save data to Firestore
                            saveFoodData(imageUrl);
                        }).addOnFailureListener(e -> {
                            // Error getting download URL
                            Toast.makeText(AddFoodActivity.this, "Error getting download URL", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Error uploading image
                        Toast.makeText(AddFoodActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // No image selected
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFoodData(String imageUrl) {
        EditText etFoodName = findViewById(R.id.etFoodName);
        EditText etFoodDescription = findViewById(R.id.etFoodDescription);
        EditText etFoodPrice = findViewById(R.id.etFoodPrice);

        String foodName = etFoodName.getText().toString();
        String foodDescription = etFoodDescription.getText().toString();
        String foodPrice = etFoodPrice.getText().toString();

        Map<String, Object> foodData = new HashMap<>();
        foodData.put("name", foodName);
        foodData.put("description", foodDescription);
        foodData.put("price", foodPrice);
        foodData.put("imageUrl", imageUrl);

        db.collection("Food").add(foodData)
                .addOnSuccessListener(documentReference -> {
                    // Food data added successfully
                    Toast.makeText(AddFoodActivity.this, "Food added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Finish activity
                })
                .addOnFailureListener(e -> {
                    // Error adding food data
                    Toast.makeText(AddFoodActivity.this, "Error adding food", Toast.LENGTH_SHORT).show();
                });
    }
}
