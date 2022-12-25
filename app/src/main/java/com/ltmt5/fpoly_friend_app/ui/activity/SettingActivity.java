package com.ltmt5.fpoly_friend_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySettingBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    ProgressDialog progressDialog;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    private void setClick() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
        binding.btnPlatium.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnGold.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnPlus.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnSupper.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnFast.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnIncognito.setOnClickListener(view -> Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());
        binding.btnLogOut.setOnClickListener(view -> {
            PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, false);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });
        binding.btnDelete.setOnClickListener(view -> {
            progressDialog.show();
            PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, false);
            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SettingActivity.this, SignInActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(SettingActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}