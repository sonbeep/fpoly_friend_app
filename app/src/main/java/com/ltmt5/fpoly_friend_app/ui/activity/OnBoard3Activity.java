package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivityOnBoard3Binding;

public class OnBoard3Activity extends AppCompatActivity {
    ActivityOnBoard3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoard3Binding.inflate(getLayoutInflater());
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