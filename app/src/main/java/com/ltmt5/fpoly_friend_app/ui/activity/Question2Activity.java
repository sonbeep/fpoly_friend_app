package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion2Binding;

public class Question2Activity extends AppCompatActivity {
    ActivityQuestion2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
//            if (validate()) {
                startActivity(new Intent(this, Question3Activity.class));
//            }
        });
    }

    boolean validate() {
        boolean isDone = true;
        if (binding.ed1.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
            isDone = false;
        } else {
            int i = Integer.parseInt(binding.ed1.getText().toString().trim());
            if (i < 1900 || i > 2022) {
                Toast.makeText(this, "Năm không hợp lệ", Toast.LENGTH_SHORT).show();
                isDone = false;
            }
        }

        return isDone;
    }
}