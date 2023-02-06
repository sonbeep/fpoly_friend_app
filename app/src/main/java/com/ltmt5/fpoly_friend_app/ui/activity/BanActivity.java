package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ltmt5.fpoly_friend_app.adapter.AddImageAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityBanBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.ArrayList;
import java.util.List;


public class BanActivity extends AppCompatActivity implements AddImageAdapter.ItemClick {
    ActivityBanBinding binding;
    UserProfile mUserProfile;
    AddImageAdapter addImageAdapter;
    List<Uri> uriList;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null) {
                    if (uriList.size() < 7) {
                        uriList.add(result.getData().getData());
                    } else {
                        Toast.makeText(BanActivity.this, "Tối đa 6", Toast.LENGTH_SHORT).show();
                    }

                }
                addImageAdapter.setData(uriList);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void initView() {
        uriList = new ArrayList<>();
        mUserProfile = (UserProfile) getIntent().getSerializableExtra(Constants.KEY_USER);
        if (mUserProfile != null) {
            binding.tvUserID.setText(mUserProfile.getUserId());
            Glide.with(this).load(mUserProfile.getImageUri()).centerCrop().into(binding.ivAvatar);
        }
        addImageAdapter = new AddImageAdapter(this, this);
        binding.recAddImage.setAdapter(addImageAdapter);
    }
    FirebaseStorage storage;
    StorageReference storageRef;

    private void setClick() {
        binding.btnAdd.setOnClickListener(v -> {
            TedPermission.create().setPermissionListener(new PermissionListener() {
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
            }).setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]").setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).check();

        });
//        binding.btnNext.setOnClickListener(v -> {
//            storageRef = storage.getReference().child("image_uri/" + user.getUid() + "/uImage");
//            storageRef.putFile(imageUri).addOnCompleteListener(this, task -> {
//                if (task.isSuccessful()) {
//                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                        userProfile.setImageUri(uri.toString());
//                        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
//                        myRef.setValue(userProfile, (error, ref) -> {
//                            progressDialog.dismiss();
//                            Log.e(TAG, "created user profile");
//                            showToast("Đăng kí thành công");
//                            updateUI();
//                        });
//                    });
//                } else {
//                    progressDialog.dismiss();
//                    Log.e(TAG, "fail");
//                    showToast("Đã xảy ra lỗi v1");
//                }
//            });
//        });
    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public void deleteItem(int position) {
        uriList.remove(position);
        addImageAdapter.notifyDataSetChanged();
    }
}