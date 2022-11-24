package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void initView() {
    }

    private void setClick() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
        binding.btnPlatium.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnGold.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnPlus.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnSupper.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnFast.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnIncognito.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnLogOut.setOnClickListener(view -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnDelete.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
    }
}