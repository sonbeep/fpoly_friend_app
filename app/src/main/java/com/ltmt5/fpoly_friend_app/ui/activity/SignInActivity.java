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
    FirebaseUser user;
    boolean isFirst;
    UserProfile mUserProfile;
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
        getUser();

    }

    private void getUser() {
        isFirst = true;
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
                    Log.e(TAG, "sign in - list profile size: " + App.userProfileList.size());
                    isFirst = false;
                }
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

        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

    void handleNextClick(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        updateUI();
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

    private void updateUI() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        for (UserProfile profile : App.userProfileList) {
            if (profile.getUserId().equals(user.getUid())) {
                mUserProfile = profile;
            }
        }
        if (mUserProfile != null) {
            if (mUserProfile.getAvailability() == -101) {
                showToast("Tài khoản đã bị khoá");
            } else {
                App.sharePref.setSignIn(true);
                App.currentUser = mUserProfile;
                App.userProfileList.remove(mUserProfile);
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        } else {
            showToast("Không tìm thấy người dùng");
        }
    }

    private boolean validate(String email, String password) {
        String emailPattern = "[a-zA-Z0-9._-]+@fpt.edu.vn";
        if (email.equals("")) {
            Toast.makeText(this, "Email không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Email không hợp lệ. Yêu cầu nhập gmail fpt.edu.vn", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LogInActivity.class));
    }
}