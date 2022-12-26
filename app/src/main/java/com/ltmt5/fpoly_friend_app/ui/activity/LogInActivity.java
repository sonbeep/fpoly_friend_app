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
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    FirebaseUser user;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isFirst = true;
        isFirst2 = true;
        initView();
        binding.btnLogIn.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
        binding.btnError.setOnClickListener(v -> Toast.makeText(this, "Vui lòng thông cảm. Chúng tôi sẽ khắc phục", Toast.LENGTH_SHORT).show());
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            progressDialog.show();
            getData();

        } else {
            binding.layoutMain.setVisibility(View.VISIBLE);
        }

    }

    private void getData() {
        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isFirst) {
                    Log.e(TAG, "log in");
                    progressDialog.dismiss();
                    try {
                        UserProfile userProfile = snapshot.getValue(UserProfile.class);
                        if (userProfile != null) {
                            if (userProfile.getAvailability() == -101) {
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, false);
                                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                                Toast.makeText(getApplicationContext(), "Tài khoản đã bị khoá", Toast.LENGTH_SHORT).show();
                                binding.layoutMain.setVisibility(View.VISIBLE);
                            } else {
                                getUser();
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "" + e);
                        binding.layoutMain.setVisibility(View.VISIBLE);
                    }
                    isFirst = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }

    boolean isFirst;
    boolean isFirst2;

    private void getUser() {
        DatabaseReference myRef = database.getReference("user_profile/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isFirst2){
                    App.userProfileList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        try {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (userProfile != null) {
                                if (userProfile.getUserId() != null) {
                                    if (userProfile.getUserId().equals(user.getUid())) {
                                        App.currentUser = userProfile;
                                    } else {
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
                    isFirst2 = false;
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