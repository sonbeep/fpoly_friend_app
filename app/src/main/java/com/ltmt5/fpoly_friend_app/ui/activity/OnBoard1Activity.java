package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityOnBoard1Binding;

public class OnBoard1Activity extends AppCompatActivity {
    ActivityOnBoard1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoard1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> startActivity(new Intent(this, OnBoard2Activity.class)));
        binding.btnSkip.setOnClickListener(v -> startActivity(new Intent(this, LogInActivity.class)));
        binding.btnSignIn.setOnClickListener(v -> startActivity(new Intent(this, LogInActivity.class)));
    }
}