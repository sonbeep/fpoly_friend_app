package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion6Binding;

public class Question6Activity extends AppCompatActivity {

    ActivityQuestion6Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}