package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySplashBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    FirebaseDatabase database;
    FirebaseUser user;
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        isFirst = true;
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        getUser();
    }

    void updateUi() {
        new Handler().postDelayed(() -> {
            if (App.sharePref.isFirstTimeLaunch()) {
                startActivity(new Intent(this, OnBoard1Activity.class));
            } else {
                if (App.sharePref.isSignIn()) {
                    for (UserProfile profile : App.userProfileList) {
                        if (profile.getUserId().equals(user.getUid())) {
                            App.currentUser = profile;
                        }
                    }
                    if (App.currentUser == null) {
                        startActivity(new Intent(this, LogInActivity.class));
                    } else {
                        App.userProfileList.remove(App.currentUser);
                        startActivity(new Intent(this, MainActivity.class));
                    }
                } else {
                    startActivity(new Intent(this, LogInActivity.class));
                }

            }
            finish();
        }, 1500);
    }

    private void getUser() {
        DatabaseReference myRef = database.getReference("user_profile/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isFirst) {
                    App.userProfileList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        try {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (userProfile != null) {
                                App.userProfileList.add(userProfile);
                            }
                        } catch (Exception e) {
                            Log.e("AAA", "get user error" + e);
                        }
                    }
                    Log.e(TAG, "splash - list profile size: " + App.userProfileList.size());
                    updateUi();
                    isFirst = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }
}