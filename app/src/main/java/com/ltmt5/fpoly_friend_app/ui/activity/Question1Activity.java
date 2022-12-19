package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion1Binding;
import com.ltmt5.fpoly_friend_app.help.PublicData;

public class Question1Activity extends AppCompatActivity {
    ActivityQuestion1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
            String name = binding.ed1.getText().toString().trim();
            if (validate(name)) {
            PublicData.profileTemp.setName(name);
            startActivity(new Intent(this, Question2Activity.class));
            }
        });
    }

    boolean validate(String name) {
        boolean isDone = true;
        if (name.equals("")) {
            Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }
}