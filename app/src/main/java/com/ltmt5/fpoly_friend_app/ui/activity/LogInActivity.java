package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltmt5.fpoly_friend_app.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.btnLogIn.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        binding.btnError.setOnClickListener(v -> {
            Toast.makeText(this, "Kệ người dùng", Toast.LENGTH_SHORT).show();
        });

    }

    private void initView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}