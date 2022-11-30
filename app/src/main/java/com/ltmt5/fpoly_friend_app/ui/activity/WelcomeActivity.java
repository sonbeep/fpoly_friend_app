package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ltmt5.fpoly_friend_app.databinding.ActivityWelcomeBinding;
import com.ltmt5.fpoly_friend_app.help.PublicData;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;
    FirebaseDatabase database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();

    }

    private void setClick() {
        Intent intent = getIntent();
        if (intent != null) {
            String text = intent.getStringExtra(PublicData.TEXT_WELCOME);
            binding.tv1.setText(text);
        }
        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
        Log.d(TAG, "id" + user.getUid());
        binding.btnNext.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog(WelcomeActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
            myRef.setValue(PublicData.profileTemp, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    dialog.dismiss();
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            });
        });
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }
}