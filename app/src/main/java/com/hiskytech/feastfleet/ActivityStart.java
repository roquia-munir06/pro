package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.hiskytech.feastfleet.databinding.ActivityStartBinding;

public class ActivityStart extends AppCompatActivity {
    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnStartSignIn.setOnClickListener(v -> {
            startActivity(new Intent(ActivityStart.this, ActivitySignin.class));
        });

        binding.btnStartSIgnUp.setOnClickListener(v -> {
            startActivity(new Intent(ActivityStart.this, ActivitySignup.class));
        });
    }
}
