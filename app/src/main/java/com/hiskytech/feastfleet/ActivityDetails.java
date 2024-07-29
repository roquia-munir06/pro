package com.hiskytech.feastfleet;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.hiskytech.feastfleet.databinding.ActivityDetailsBinding;

public class ActivityDetails extends AppCompatActivity {
    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String imageUrl = getIntent().getStringExtra("imageUrl");

        binding.tvFoodName.setText(getIntent().getStringExtra("name"));
        binding.tvDescription.setText(getIntent().getStringExtra("description"));
        binding.tvPrice.setText(getIntent().getStringExtra("price"));

        Glide.with(this).load(imageUrl).centerCrop().into(binding.imageViewDetail);

        binding.btnCart.setOnClickListener(v -> {
            Toast.makeText(ActivityDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
        binding.btnOrder.setOnClickListener(v -> {
            Toast.makeText(ActivityDetails.this, "Your Order has been placed", Toast.LENGTH_SHORT).show();
        });
    }
}
