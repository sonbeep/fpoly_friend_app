package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivityLogInBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding;
    FirebaseDatabase database;
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.btnLogIn.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        binding.btnError.setOnClickListener(v -> Toast.makeText(this, "Vui lòng thông cảm. Chúng tôi sẽ khắc phục", Toast.LENGTH_SHORT).show());
    }

    private void initView() {
        if (App.userProfileList.size() == 0) {
            getUser();
        }
    }

    private void getUser() {
        isFirst = true;
        database = FirebaseDatabase.getInstance();
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
                    Log.e(TAG, "log in - list profile size: " + App.userProfileList.size());
                    isFirst = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}