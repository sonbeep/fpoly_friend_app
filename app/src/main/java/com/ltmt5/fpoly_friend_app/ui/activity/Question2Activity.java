package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion1Binding;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion2Binding;

public class Question2Activity extends AppCompatActivity {
    ActivityQuestion2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}