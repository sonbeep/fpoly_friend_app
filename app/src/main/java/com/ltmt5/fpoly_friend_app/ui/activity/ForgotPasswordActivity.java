package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.ltmt5.fpoly_friend_app.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intiView();
        setClick();
    }

    private void intiView() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void setClick() {
        binding.btnNext.setOnClickListener(view -> {
            String email = binding.edUsername.getText().toString().trim();
            if (validate(email)) {
                onSent(email);
            }
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnSignIn.setOnClickListener(view -> startActivity(new Intent(this, SignInActivity.class)));
    }

    private void onSent(String phoneNumber) {
        mAuth.sendPasswordResetEmail(phoneNumber)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    private void loadOTPActivity(String phoneNumber, String id) {
//        Intent intent = new Intent(ForgotPasswordActivity.this, OTPActivity.class);
//        intent.putExtra("phoneNumber", phoneNumber);
//        intent.putExtra("id", id);
//        startActivity(intent);
//    }

    private void loadMainActivity(String phoneNumber) {
        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            loadMainActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ForgotPasswordActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private boolean validate(String email) {
        boolean isDone = true;
        if (email.equals("")) {
            isDone = false;
        }
        return isDone;
    }
}