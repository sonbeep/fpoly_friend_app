package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
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
        binding.btnNext.setOnClickListener(v -> {
            if (validate()) {
                startActivity(new Intent(this, Question6Activity.class));
            }
        });
    }

    boolean validate() {
        boolean isDone = true;
//        if (binding.ed1.getText().toString().trim().equals("")) {
//            Toast.makeText(this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
//            isDone = false;
//        }
        return isDone;
    }
}