package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivityWelcomeBinding;
import com.ltmt5.fpoly_friend_app.help.PublicData;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null) {
            String text = intent.getStringExtra(PublicData.TEXT_WELCOME);
            binding.tv1.setText(text);
        }
        binding.btnNext.setOnClickListener(v -> {
            App.sharePref.setSignIn(true);
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}