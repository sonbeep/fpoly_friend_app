package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion1Binding;

public class Question1Activity extends AppCompatActivity {
    ActivityQuestion1Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button1.setOnClickListener(v -> startActivity(new Intent(this, PersonalInforActivity.class)));
        binding.button1.setOnClickListener(v -> startActivity(new Intent(this, PersonalInforActivity.class)));
    }
}