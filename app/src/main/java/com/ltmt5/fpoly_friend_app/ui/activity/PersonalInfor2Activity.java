package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityPersonalInfor2Binding;
import com.ltmt5.fpoly_friend_app.databinding.ActivityPersonalInforBinding;

public class PersonalInfor2Activity extends AppCompatActivity {
    ActivityPersonalInfor2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfor2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> startActivity(new Intent(this, WelcomeActivity.class)));
    }
}