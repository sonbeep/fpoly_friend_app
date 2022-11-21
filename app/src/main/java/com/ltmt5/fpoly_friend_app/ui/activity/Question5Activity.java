package com.ltmt5.fpoly_friend_app.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion5Binding;

public class Question5Activity extends AppCompatActivity {

    ActivityQuestion5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}