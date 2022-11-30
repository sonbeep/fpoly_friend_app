package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySignUpBinding;
import com.ltmt5.fpoly_friend_app.ui.dialog.SignUpDialog;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void setClick() {
        binding.btnSignIn.setOnClickListener(view -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(view -> {
            String email = binding.edUsername.getText().toString().trim();
            String password = binding.edPassword.getText().toString().trim();
//            if(validate(email,password)){
                handleSignUpClick(email, password);
//            }
        });
        binding.btnBack.setOnClickListener(view -> onBackPressed());
    }

    private boolean validate(String email, String password) {
        boolean isDone = true;
        String emailPattern = "[a-zA-Z0-9._-]+@fpt.edu.vn";
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email, password không được để trống", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "Password quá ngắn", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

    }

    private void handleSignUpClick(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI() {
        SignUpDialog signUpDialog = SignUpDialog.newInstance();
        signUpDialog.showAllowingStateLoss(getSupportFragmentManager(), "back");
        signUpDialog.setOnClickListener(new SignUpDialog.OnClickListener() {

            @Override
            public void onApply() {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }

            @Override
            public void onCancel() {

            }
        });
    }
}