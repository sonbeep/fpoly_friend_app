package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySplashBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    FirebaseDatabase database;
    List<UserProfile> userProfileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_profile/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        userProfileList.add(userProfile);
                    }
                    Log.e(TAG, "userProfileList" + userProfileList.size());
                    App.userProfileList.addAll(userProfileList);
                    App.userProfileList.addAll(userProfileList);
                    App.userProfileList.addAll(userProfileList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });

        new Handler().postDelayed(() -> {
            Log.e(TAG, "profile list size: " + App.userProfileList.size());
            if (App.sharePref.isFirstTimeLaunch()) {
                startActivity(new Intent(this, OnBoard1Activity.class));
            } else {
                startActivity(new Intent(this, LogInActivity.class));
            }
            finish();
        }, 1500);
    }
}