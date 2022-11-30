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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySignInBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    UserProfile userProfile;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intiView();
        setClick();
    }

    private void intiView() {
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    private void setClick() {
        binding.btnSignIn.setOnClickListener(view -> {
            String email = binding.edUsername.getText().toString().trim();
            String password = binding.edPassword.getText().toString().trim();
            if (validate(email, password)) {
                handleNextClick(email, password);
            }
        });
        binding.tvForgot.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        binding.btnSignUp.setOnClickListener(view -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
    }

    void handleNextClick(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(UserProfile.class);
                if (userProfile != null) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SignInActivity.this, Question1Activity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private boolean validate(String email, String password) {
        boolean isDone = true;
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email, password không được để trống", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }


}