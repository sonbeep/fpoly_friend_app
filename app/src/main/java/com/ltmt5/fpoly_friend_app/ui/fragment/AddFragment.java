package com.ltmt5.fpoly_friend_app.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.ltmt5.fpoly_friend_app.adapter.AddImageAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentAddBinding;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class AddFragment extends Fragment implements AddImageAdapter.ItemClick {
    public static int positionAdd = 0;
    public static List<Uri> uriList = new ArrayList<>();
    FragmentAddBinding binding;
    AddImageAdapter addImageAdapter;
    MainActivity mainActivity;
    Context context;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseUser user;
    private Uri imageUri;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null) {
                    imageUri = result.getData().getData();
                }
                updateImgage();
            }
        }
    });

    private void updateImgage() {
        progressDialog.show();
        storageRef = storage.getReference().child("image_uri/" + user.getUid() + "/uStory");
        storageRef.putFile(imageUri).addOnCompleteListener(mainActivity, task -> {
            if (task.isSuccessful()) {
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    DatabaseReference myRef = database.getReference("user_image/" + user.getUid() + "/" + positionAdd);
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

    private void deleteImgage() {
        progressDialog.show();
        DatabaseReference myRef = database.getReference("user_image/" + user.getUid() + "/" + positionAdd);
        myRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.e(TAG, "delete image");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater);
        initView();
        setClick();
        getImage();
        return binding.getRoot();
    }

    void getImage() {
        uriList.clear();
        progressDialog.show();
        DatabaseReference myRef = database.getReference("user_image/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        String uri = dataSnapshot.getValue(String.class);
                        if (uri != null) {
                            uriList.add(Uri.parse(uri));
                        }

                    } catch (Exception e) {
                        Log.e("AAA", "" + e);
                    }
                }
                addImageAdapter.setData(uriList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        context = App.context;
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        addImageAdapter = new AddImageAdapter(context, this);
        binding.recAddImage.setAdapter(addImageAdapter);
    }

    private void setClick() {
        binding.btnNext.setOnClickListener(v -> {
            positionAdd = uriList.size();
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void deleteItem(int position) {
        uriList.remove(position);
        addImageAdapter.notifyDataSetChanged();
        positionAdd = position;
        deleteImgage();
    }
}