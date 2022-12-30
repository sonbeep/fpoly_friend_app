package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivityOnBoard2Binding;

public class OnBoard2Activity extends AppCompatActivity {
    ActivityOnBoard2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoard2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> startActivity(new Intent((Context) this, OnBoard3Activity.class)));
        binding.btnSkip.setOnClickListener(v -> {
            App.sharePref.setFirstTimeLaunch(false);
            startActivity(new Intent((Context) this, LogInActivity.class));
        });
        binding.btnSignIn.setOnClickListener(v -> {
            App.sharePref.setFirstTimeLaunch(false);
            startActivity(new Intent((Context) this, SignInActivity.class));
        });
    }
}