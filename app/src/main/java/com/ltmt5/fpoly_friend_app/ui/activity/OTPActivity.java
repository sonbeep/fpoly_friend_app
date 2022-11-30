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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ltmt5.fpoly_friend_app.databinding.ActivityOtpactivityBinding;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding binding;
    String phoneNumber;
    String id;
    PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDataIntent();
        intiView();
        setClick();
    }

    private void setClick() {
        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.btnNext.setOnClickListener(view -> {
            String otp = binding.edOTP.getText().toString().trim();
            if (otp.equals("")) {
                Toast.makeText(this, "OTP không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                handleClickSend(otp);
            }

        });
        binding.tvResend.setOnClickListener(view -> {
            handleClickSendAgain();
        });
    }

    private void intiView() {
        mAuth = FirebaseAuth.getInstance();
    }

    void handleClickSend(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, code);
        signInWithPhoneAuthCredential(credential);
    }

    void handleClickSendAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setForceResendingToken(mForceResendingToken)// Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTPActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String strId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(strId, forceResendingToken);
                                id = strId;
                                mForceResendingToken = forceResendingToken;

                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        id = intent.getStringExtra("id");
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
                                Toast.makeText(OTPActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void loadMainActivity(String phoneNumber) {
        Intent intent = new Intent(OTPActivity.this, MainActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }
}