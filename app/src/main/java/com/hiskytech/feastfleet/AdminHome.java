package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        AppCompatButton btnAddFood = findViewById(R.id.btnAddUFood);
        AppCompatButton btnViewUsers = findViewById(R.id.btnUser);

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });

        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, ViewUsersActivity.class);
                startActivity(intent);
            }
        });
    }
}
