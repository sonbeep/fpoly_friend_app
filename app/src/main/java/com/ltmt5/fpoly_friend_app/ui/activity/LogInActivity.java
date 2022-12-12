package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.ltmt5.fpoly_friend_app.databinding.ActivityLogInBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.btnLogIn.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        binding.btnError.setOnClickListener(v -> {
            Toast.makeText(this, "Kệ người dùng", Toast.LENGTH_SHORT).show();
        });
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            progressDialog.show();
            database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("user_profile/");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        try {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (userProfile != null) {
                                if (userProfile.getUserId() != null) {
                                    if (userProfile.getUserId().equals(user.getUid())) {
                                        App.currentUser = userProfile;
                                    }
                                    else {
                                        App.userProfileList.add(userProfile);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            Log.e("AAA", "" + e);
                        }

                    }
                    Log.e(TAG, "list profile size: " + App.userProfileList.size());
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "profile list empty");
                }
            });
        } else {
            binding.layoutMain.setVisibility(View.VISIBLE);
        }
    }
}