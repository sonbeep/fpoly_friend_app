package com.ltmt5.fpoly_friend_app.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityPersonalInforBinding;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion1Binding;

public class PersonalInforActivity extends AppCompatActivity {
    ActivityPersonalInforBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInforBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> startActivity(new Intent(this, PersonalInfor2Activity.class)));
    }
}