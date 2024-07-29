package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ActivityUserSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        AppCompatButton btnUser = findViewById(R.id.btnUser);
        AppCompatButton btnAdmin = findViewById(R.id.btnAdmin);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityUserSelection.this, ActivityStart.class);
                startActivity(intent);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityUserSelection.this, AdminLogin.class);
                startActivity(intent);
            }
        });
    }
}
