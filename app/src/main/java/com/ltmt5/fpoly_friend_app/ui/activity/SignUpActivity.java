package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ltmt5.fpoly_friend_app.BuildConfig;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySignUpBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.dialog.SignUpDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ProgressDialog progressDialog;
    Uri imageUri;
    File fileCamera;
    boolean isDone;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private String encodedImage;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Bitmap bitmap = null;
                if (result.getData() == null) {
                    bitmap = getBitmapFromUri(imageUri);
                } else {
                    imageUri = result.getData().getData();
                    bitmap = getBitmapFromUri(imageUri);
                }
                encodedImage = EncodeImage(bitmap);
                binding.imageProfile.setImageBitmap(bitmap);
                binding.textAddImage.setVisibility(View.INVISIBLE);
            }
        }
    });
    private PreferenceManager preferenceManager;

    private String EncodeImage(Bitmap bitmap) {
        int previewWith = 150;
        int previewHeight = bitmap.getHeight() * previewWith / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWith, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

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
        binding.imageProfile.setOnClickListener(view -> {
            TedPermission.create().setPermissionListener(new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    launcher.launch(getIntentImage());
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {

                }
            }).setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]").setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).check();


        });
    }

    private boolean validate() {
        String name = binding.edName.getText().toString().trim();
        String email = binding.edUsername.getText().toString().trim();
        String password = binding.edPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@fpt.edu.vn";
        if (email.equals("") || password.equals("") || name.equals("")) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password quá ngắn", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (encodedImage == null) {
            Toast.makeText(this, "Ảnh không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

    }

    private void handleSignUpClick(String email, String password) {
        isDone = true;
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            updateProfile();
//                            updateFireStore();
                            if (isDone) {
                                updateUI();
                            } else {
                                showToast("Đã xảy ra lỗi");
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateFireStore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, binding.edName.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.edUsername.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.edPassword.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, binding.edUsername.getText().toString());
                    preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);

                })
                .addOnFailureListener(e -> {
                    isDone = false;
                    Log.e("AAA", e.getMessage());
//                    showToast(e.getMessage());
                });
    }

    private void updateProfile() {
        String name = binding.edName.getText().toString().trim();
        String email = binding.edUsername.getText().toString().trim();
        String password = binding.edPassword.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfile userProfile = new UserProfile();

        if (user != null) {
            userProfile.setAvailability(-1);
            userProfile.setUserId(user.getUid());
            userProfile.setEmail(email);
            userProfile.setPassword(password);
//            userProfile.setImageUri(imageUri.toString());
            userProfile.setImageUri(encodedImage);

            DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
            myRef.setValue(userProfile, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Log.e(TAG, "created user profile");
                }
            });

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(imageUri)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.e(TAG, "user updated");
                            } else {
                                isDone = false;
                            }
                        }
                    });

        } else {
            Log.e(TAG, "user null");
        }


    }

    private void updateUI() {
        SignUpDialog signUpDialog = SignUpDialog.newInstance();
        signUpDialog.showAllowingStateLoss(getSupportFragmentManager(), "sign_up");
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

    public Bitmap getBitmapFromUri(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Uri createCameraUri() {
        String nameTest = "camera_" + System.currentTimeMillis();
        try {
            fileCamera = File.createTempFile(nameTest, ".png");
        } catch (IOException e) {
            return null;
        }
        return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", fileCamera);
    }

    public Intent getIntentImage() {
        imageUri = createCameraUri();
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        pickIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.putExtra(Intent.EXTRA_INDEX, 0);


        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        return chooserIntent;
    }

    private void showToast(String meesage) {
        Toast.makeText(this, meesage, Toast.LENGTH_SHORT).show();
    }


}