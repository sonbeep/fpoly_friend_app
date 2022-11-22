package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion2Binding;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion4Binding;

public class Question4Activity extends AppCompatActivity {
    ActivityQuestion4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
//            if (validate()) {
                startActivity(new Intent(this, Question5Activity.class));
//            }
        });
    }

    boolean validate() {
        boolean isDone = true;
        if (binding.ed1.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Chuyên ngành không được để trống", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }
}