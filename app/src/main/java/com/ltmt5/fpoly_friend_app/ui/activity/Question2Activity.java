package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion2Binding;
import com.ltmt5.fpoly_friend_app.help.PublicData;

public class Question2Activity extends AppCompatActivity {
    ActivityQuestion2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
            String age = binding.ed1.getText().toString().trim();
            if (validate(age)) {
                PublicData.profileTemp.setAge(Integer.parseInt(binding.ed1.getText().toString().trim()));
                startActivity(new Intent(this, Question3Activity.class));
            }
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

    boolean validate(String age2) {
        boolean isDone = true;
        if (age2.equals("")) {
            Toast.makeText(this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
            isDone = false;
        } else {
            int age = Integer.parseInt(binding.ed1.getText().toString().trim());
            if (age < 1970 || age > 2022) {
                Toast.makeText(this, "Năm không hợp lệ", Toast.LENGTH_SHORT).show();
                isDone = false;
            }
        }

        return isDone;
    }
}