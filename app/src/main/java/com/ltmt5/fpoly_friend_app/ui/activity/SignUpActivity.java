package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.Manifest;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ltmt5.fpoly_friend_app.databinding.ActivitySignUpBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.dialog.EducationDialog;
import com.ltmt5.fpoly_friend_app.ui.dialog.GenderDialog;
import com.ltmt5.fpoly_friend_app.ui.dialog.SignUpDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ProgressDialog progressDialog;
    Uri imageUri;
    boolean isDone;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageRef;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Bitmap bitmap = null;
                if (result.getData() != null) {
                    imageUri = result.getData().getData();
                    bitmap = getBitmapFromUri(imageUri);
                }
                binding.imageProfile.setImageBitmap(bitmap);
                binding.textAddImage.setVisibility(View.INVISIBLE);
            }
        }
    });
    private FirebaseAuth mAuth;

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
        binding.btnGender.setOnClickListener(v -> {
            GenderDialog genderDialog = GenderDialog.newInstance();
            genderDialog.showAllowingStateLoss(getSupportFragmentManager(), "genderDialog");
            genderDialog.setOnClickListener(new GenderDialog.OnClickListener() {
                @Override
                public void onApply(String data) {
                    if (data != null) {
                        binding.edGender.setText(data);
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        });

        binding.btnEducation.setOnClickListener(v -> {
            Log.e(TAG, "btnEducation: ");
            EducationDialog educationDialog = EducationDialog.newInstance();
            educationDialog.showAllowingStateLoss(getSupportFragmentManager(), "educationDialog");
            educationDialog.setOnClickListener(new EducationDialog.OnClickListener() {
                @Override
                public void onApply(String data) {
                    if (data != null) {
                        binding.edEducation.setText(data);
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        });


        binding.btnSignIn.setOnClickListener(view -> startActivity(new Intent(this, SignInActivity.class)));
        binding.btnSignUp.setOnClickListener(view -> {
            String name = binding.edName.getText().toString().trim();
            String phone = binding.edPhone.getText().toString().trim();
            String email = binding.edUsername.getText().toString().trim();
            String password = binding.edPassword.getText().toString().trim();
            String age = binding.edAge.getText().toString().trim();
            String gender = binding.edGender.getText().toString().trim();
            String education = binding.edEducation.getText().toString().trim();
            if (validate(name, phone, email, password, age, gender, education)) {
                handleSignUpClick(email, password);
            }
        });
        binding.btnBack.setOnClickListener(view -> onBackPressed());
        binding.imageProfile.setOnClickListener(view -> TedPermission.create().setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(pickIntent);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        }).setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]").setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).check());
    }


    private void initView() {
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

    }

    private void handleSignUpClick(String email, String password) {
        isDone = true;
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                updateProfile();
            } else {
                // If sign in fails, display a message to the user.
                progressDialog.dismiss();
                Log.e(TAG, "Authentication failed.", task.getException());
                Toast.makeText(SignUpActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String name = binding.edName.getText().toString().trim();
        int phone = Integer.parseInt(binding.edPhone.getText().toString().trim());
        String email = binding.edUsername.getText().toString().trim();
        String password = binding.edPassword.getText().toString().trim();
        int age = Integer.parseInt(binding.edAge.getText().toString().trim());
        String gender = binding.edGender.getText().toString().trim();
        String education = binding.edEducation.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfile userProfile = new UserProfile();

        if (user != null) {
            List<String> list = new ArrayList<>();
            list.add("Trống");
            userProfile.setAvailability(0);
            userProfile.setUserId(user.getUid());
            userProfile.setPhone(phone);
            userProfile.setEmail(email);
            userProfile.setPassword(password);
            userProfile.setName(name);
            userProfile.setAge(age);
            userProfile.setMatch(0);
            userProfile.setGender(gender);
            userProfile.setEducation(education);
            userProfile.setHobbies(list);
            storageRef = storage.getReference().child("image_uri/" + user.getUid() + "/uImage");
            storageRef.putFile(imageUri).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        userProfile.setImageUri(uri.toString());
                        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
                        myRef.setValue(userProfile, (error, ref) -> {
                            progressDialog.dismiss();
                            Log.e(TAG, "created user profile");
                            showToast("Đăng kí thành công");
                            updateUI();
                        });
                    });
                } else {
                    progressDialog.dismiss();
                    Log.e(TAG, "fail");
                    showToast("Đã xảy ra lỗi v1");
                }
            });
        } else {
            Log.e(TAG, "user null");
            showToast("Đã xảy ra lỗi");
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

    private void showToast(String meesage) {
        Toast.makeText(this, meesage, Toast.LENGTH_SHORT).show();
    }

    private boolean validate(String name, String phone, String email, String password, String age, String gender, String education) {
        String emailPattern = "[a-zA-Z0-9._-]+@fpt.edu.vn";
        String phonePattern = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        if (name.equals("")) {
            Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.equals("")) {
            Toast.makeText(this, "Số điện thoại  không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.length() != 10 && !phone.matches(phonePattern)) {
            Toast.makeText(this, "Số điện thoại không đúng định dạng ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.equals("")) {
            Toast.makeText(this, "Email không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Email không hợp lệ. Yêu cầu nhập gmail fpt.edu.vn", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (age.equals("")) {
            Toast.makeText(this, "Năm sinh không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (1970 > Integer.parseInt(age) || 2023 < Integer.parseInt(age)) {
            Toast.makeText(this, "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (gender.equals("Chọn giới tính")) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        } else if (education.equals("Chọn chuyên ngành")) {
            Toast.makeText(this, "Vui lòng chọn chuyên ngành", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password yêu cầu từ 8 ký tự trở lên", Toast.LENGTH_SHORT).show();
            return false;
        } else if (imageUri == null) {
            Toast.makeText(this, "Vui lòng thêm ảnh", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}
