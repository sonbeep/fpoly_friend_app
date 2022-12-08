package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion3Binding;
import com.ltmt5.fpoly_friend_app.help.PublicData;

public class Question3Activity extends AppCompatActivity {
    ActivityQuestion3Binding binding;
    String mGender = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNext.setOnClickListener(v -> {
//            if (!mGender.equals("none")) {
//                PublicData.gender = mGender;
            PublicData.profileTemp.setGender(mGender);
            startActivity(new Intent(this, Question4Activity.class));
//            }
        });
        binding.btnMale.setOnClickListener(v -> {
            mGender = "Nam";

            binding.btnMale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
            binding.tvMale.setTextColor(ContextCompat.getColor(this, R.color.white));

            binding.btnFemale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            binding.tvFemale.setTextColor(ContextCompat.getColor(this, R.color.prime_1));

            binding.btnOther.setCardBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            binding.tvOther.setTextColor(ContextCompat.getColor(this, R.color.prime_1));

            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));

        });

        binding.btnFemale.setOnClickListener(v -> {
            mGender = "Nữ";

            binding.btnFemale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
            binding.tvFemale.setTextColor(ContextCompat.getColor(this, R.color.white));

            binding.btnMale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            binding.tvMale.setTextColor(ContextCompat.getColor(this, R.color.prime_1));

            binding.btnOther.setCardBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            binding.tvOther.setTextColor(ContextCompat.getColor(this, R.color.prime_1));

            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
        });

        binding.btnOther.setOnClickListener(v -> {
            mGender = "Khác";

            binding.btnOther.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
            binding.tvOther.setTextColor(ContextCompat.getColor(this, R.color.white));

            binding.btnFemale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            binding.tvMale.setTextColor(ContextCompat.getColor(this, R.color.prime_1));

            binding.btnMale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            binding.tvMale.setTextColor(ContextCompat.getColor(this, R.color.prime_1));

            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
        });
    }

}