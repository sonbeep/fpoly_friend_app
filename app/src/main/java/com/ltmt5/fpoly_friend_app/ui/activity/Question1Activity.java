package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion1Binding;

public class Question1Activity extends AppCompatActivity {
    ActivityQuestion1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
            if (validate()) {
                startActivity(new Intent(this, Question2Activity.class));
            }
        });
    }

    boolean validate() {
        boolean isDone = true;
        if (binding.ed1.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }
}