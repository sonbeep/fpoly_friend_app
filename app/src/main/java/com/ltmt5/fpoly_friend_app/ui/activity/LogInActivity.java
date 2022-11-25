package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        if (App.sharePref.isSignIn()) {
//            startActivity(new Intent(this, MainActivity.class));
//        } else {
            binding.btnLogIn.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
            binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
            binding.btnError.setOnClickListener(v -> {
                Toast.makeText(this, "Kệ người dùng", Toast.LENGTH_SHORT).show();
            });
//        }

    }
}