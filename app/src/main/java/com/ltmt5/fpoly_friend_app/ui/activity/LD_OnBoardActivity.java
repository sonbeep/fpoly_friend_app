package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityLdOnBoardBinding;

public class LD_OnBoardActivity extends AppCompatActivity {
    ActivityLdOnBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLdOnBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
            App.sharePref.setFirstTimeLaunch(false);
            startActivity(new Intent(this, LogInActivity.class));
        });
        binding.btnSignIn.setOnClickListener(v -> {
            App.sharePref.setFirstTimeLaunch(false);
            startActivity(new Intent(this, SignInActivity.class));
        });
    }
}