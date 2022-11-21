package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion2Binding;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion3Binding;

public class Question3Activity extends AppCompatActivity {

    ActivityQuestion3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}