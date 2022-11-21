package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.adapter.AddImageAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion6Binding;

import java.util.ArrayList;
import java.util.List;

public class Question6Activity extends AppCompatActivity implements AddImageAdapter.ItemClick {

    ActivityQuestion6Binding binding;
    List<Bitmap> list = new ArrayList<>();
    AddImageAdapter addImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void initView() {
        for (int i = 0; i < 6; i++) {
            list.add(null);

        }
        addImageAdapter = new AddImageAdapter(list, this, this);
        binding.recAddImage.setAdapter(addImageAdapter);
    }

    private void setClick() {
        binding.btnNext.setOnClickListener(v -> {
            startActivity(new Intent(this, WelcomeActivity.class));
        });
    }

    @Override
    public void clickItem(int position) {
        list.remove(position);
    }

    boolean checkBitmap() {
        boolean isDone = true;
        return isDone;
    }
}