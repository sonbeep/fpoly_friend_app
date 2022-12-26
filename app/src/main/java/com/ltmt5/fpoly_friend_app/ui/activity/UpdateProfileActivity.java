package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.AddImageAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityUpdateProfileBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.dialog.UpdateProfileDialog;

import java.util.ArrayList;
import java.util.List;


public class UpdateProfileActivity extends AppCompatActivity implements AddImageAdapter.ItemClick {
    public static int positionAdd = 0;
    public static List<Uri> uriList = new ArrayList<>();
    ActivityUpdateProfileBinding binding;
    AddImageAdapter addImageAdapter;
    Context context;
    FirebaseUser user;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageRef;
    UserProfile userProfile;
    private Uri imageUri;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null) {
                    imageUri = result.getData().getData();
                }
//                uriList.add(imageUri);
//                addImageAdapter.notifyDataSetChanged();
                Glide.with(context).load(imageUri).error(R.drawable.demo).centerCrop().into(binding.imageProfile);
                updateImgage();
            }
        }
    });

    private void updateImgage() {
        progressDialog.show();
        storageRef = storage.getReference().child("image_uri/" + user.getUid() + "/uImage");
        storageRef.putFile(imageUri).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    DatabaseReference myRef = database.getReference("user_profile/" + user.getUid() + "/imageUri");
                    myRef.setValue(uri.toString(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Log.e(TAG, "changed image");
                        }
                    });
                });
            } else {
                Log.e(TAG, "fail");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
        loadUser();
    }

    private void loadUser() {
        progressDialog.show();
        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                try {
                    userProfile = snapshot.getValue(UserProfile.class);
                } catch (Exception e) {
                    Log.e(TAG, "" + e);

                }
                if (userProfile != null) {
                    getData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "load user for update fail");
            }
        });
    }

    private void getData() {
        Glide.with(context).load(userProfile.getImageUri()).error(R.drawable.demo).centerCrop().into(binding.imageProfile);

        //name
        if (userProfile.getName() != null) {
            binding.tvName.setText(userProfile.getName());
        }
        //age
        if (userProfile.getAge() < 1970 || userProfile.getAge() > 2022) {
            binding.tvAge.setText("Trống");
        } else {
            binding.tvAge.setText("" + userProfile.getAge());
        }

        //gender

        if (userProfile.getGender() != null) {
            binding.tvGender.setText(userProfile.getGender());
        }

        //education

        if (userProfile.getEducation() != null) {
            binding.tvEducation.setText(userProfile.getEducation());
        }

        //hoobies

        if (userProfile.getHobbies() != null) {
            binding.tvHoobies.setText(userProfile.getHobbies().get(0));
        }

        //description

        if (userProfile.getDescription() != null) {
            binding.tvDescription.setText(userProfile.getDescription());
        }

        //location

        if (userProfile.getLocation() != null) {
            binding.tvLocation.setText(userProfile.getLocation());
        }

        //zodiac

        if (userProfile.getZodiac() != null) {
            binding.tvZodiac.setText(userProfile.getZodiac());
        }

//        personality

        if (userProfile.getPersonality() != null) {
            binding.tvPersonality.setText(userProfile.getPersonality());
        }

//        favorite

        if (userProfile.getFavoriteSong() != null) {
            binding.tvFavoriteSong.setText(userProfile.getFavoriteSong());
        }

//        orient

        if (userProfile.getSexualOrientation() != null) {
            binding.tvSexualOrientation.setText(userProfile.getSexualOrientation());
        }

//        priority

        if (userProfile.getShowPriority() != null) {
            binding.tvShowPriority.setText(userProfile.getShowPriority());
        }


    }

    private void initView() {
        context = App.context;
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        uriList.clear();
        for (int i = 0; i < 6; i++) {
            uriList.add(null);

        }
//        addImageAdapter = new AddImageAdapter(context, this);
//        binding.recAddImage.setAdapter(addImageAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


    }

    private void setClick() {
        binding.btnAdd.setOnClickListener(view -> {
            Intent pickIntent = new Intent();
            pickIntent.setType("image/*");
            pickIntent.setAction(Intent.ACTION_GET_CONTENT);
            launcher.launch(pickIntent);
        });

        binding.btnNext.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.btnBack.setOnClickListener(view -> onBackPressed());

        binding.name.setOnClickListener(view -> {
            openDialog("name", binding.tvName.getText().toString().trim());
        });
        binding.age.setOnClickListener(view -> {
            openDialog("age", binding.tvAge.getText().toString().trim());
        });
        binding.gender.setOnClickListener(view -> {
            openDialog("gender", binding.tvGender.getText().toString().trim());
        });
        binding.education.setOnClickListener(view -> {
            openDialog("education", binding.tvEducation.getText().toString().trim());
        });
        binding.hoobies.setOnClickListener(view -> {
            openDialog("hobbies", binding.tvHoobies.getText().toString().trim());
        });
        binding.description.setOnClickListener(view -> {
            openDialog("description", binding.tvDescription.getText().toString().trim());
        });
        binding.location.setOnClickListener(view -> {
            openDialog("location", binding.tvLocation.getText().toString().trim());
        });
        binding.zodiac.setOnClickListener(view -> {
            openDialog("zodiac", binding.tvZodiac.getText().toString().trim());
        });
        binding.personality.setOnClickListener(view -> {
            openDialog("personality", binding.tvPersonality.getText().toString().trim());
        });
        binding.favoriteSong.setOnClickListener(view -> {
            openDialog("favoriteSong", binding.tvFavoriteSong.getText().toString().trim());
        });
        binding.sexualOrientation.setOnClickListener(view -> {
            openDialog("sexualOrientation", binding.tvSexualOrientation.getText().toString().trim());
        });
        binding.showPriority.setOnClickListener(view -> {
            openDialog("showPriority", binding.tvShowPriority.getText().toString().trim());
        });
    }

    public void openDialog(String name, String data) {
        UpdateProfileDialog updateProfileDialog = UpdateProfileDialog.newInstance(name, data);
        updateProfileDialog.showAllowingStateLoss(getSupportFragmentManager(), "update");
        updateProfileDialog.setOnClickListener(new UpdateProfileDialog.OnClickListener() {
            @Override
            public void onApply(String data, List<String> list) {
                if (data == null) {
                    Log.e(TAG, "ok");
                    handleUpdate(name, list);
                } else {
                    handleUpdate(name, data);
                }


            }

            @Override
            public void onCancel() {

            }
        });
    }


    void handleUpdate(String name, String data) {
        progressDialog.show();
        if (name.equals("age")) {
            int age = Integer.parseInt(data);
            DatabaseReference myRef = database.getReference("user_profile/" + user.getUid() + "/" + name);
            myRef.setValue(age, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            DatabaseReference myRef = database.getReference("user_profile/" + user.getUid() + "/" + name);
            myRef.setValue(data, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    void handleUpdate(String name, List<String> list) {
        progressDialog.show();
        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid() + "/" + name);
        myRef.setValue(list, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void clickItem(int position) {
        positionAdd = position;
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

    }

    @Override
    public void deleteItem(int position) {
//        bitmapList.remove(position);
    }

    boolean checkBitmap() {
        boolean isDone = true;
        int count = 0;
//        for (Bitmap bitmap : bitmapList) {
//            if (bitmap != null) {
//                count++;
//            }
//        }
        if (count < 2) {
            Toast.makeText(this, "Vui lòng chọn ít nhất 2 ảnh", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}