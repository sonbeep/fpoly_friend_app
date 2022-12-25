package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySignInBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    UserProfile userProfile;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intiView();
        setClick();
    }

    private void intiView() {
        preferenceManager = new PreferenceManager(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    void loadUser() {

        progressDialog.show();
        DatabaseReference myRef = database.getReference("user_profile/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                        if (userProfile != null) {
                            if (userProfile.getUserId().equals(user.getUid())) {
                                App.currentUser = userProfile;
                            } else {
                                App.userProfileList.add(userProfile);
                            }

                        }
                    } catch (Exception e) {
                        Log.e("AAA", "" + e);
                    }
                }
                Log.e(TAG, "list profile size: " + App.userProfileList.size());
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
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
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
//                            signIn();
                        updateUI(user);
                    } else {
                        Log.e(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(SignInActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signIn() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.edUsername.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.edPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                    } else {
                        showToast("Unable to sign in");
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        preferenceManager.putString(Constants.KEY_USER_ID, user.getUid());

        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(UserProfile.class);
                if (userProfile != null) {
                    if (userProfile.getAvailability() == -1) {
//                        startActivity(new Intent(SignInActivity.this, Question1Activity.class));
                    } else if (userProfile.getAvailability() == -101) {
                        Toast.makeText(SignInActivity.this, "Tài khoản đã bị khoá", Toast.LENGTH_SHORT).show();
                    } else if (userProfile.getAvailability() == 0) {
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_NAME, userProfile.getName());
                        preferenceManager.putString(Constants.KEY_IMAGE, userProfile.getImageUri());
                        loadUser();
                    } else {
                        Log.e(TAG, "Đã xảy ra lỗi1");
                    }
                } else {
                    Log.e(TAG, "Đã xảy ra lỗi");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private boolean validate(String email, String password) {
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email, password không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void showToast(String meesage) {
        Toast.makeText(this, meesage, Toast.LENGTH_SHORT).show();
    }


}