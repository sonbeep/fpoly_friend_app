package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogIn.setOnClickListener(v -> startActivity(new Intent(this, Question1Activity.class)));
        binding.btnError.setOnClickListener(v -> {
//            startActivity(new Intent(this, LogInActivity.class));
            Toast.makeText(this, "Kệ người dùng", Toast.LENGTH_SHORT).show();
        });
    }
}