package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.BuildConfig;
import com.ltmt5.fpoly_friend_app.adapter.AddImageAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentAddBinding;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.WelcomeActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddFragment extends Fragment implements AddImageAdapter.ItemClick {
    public static int positionAdd = 0;
    public static List<Uri> bitmapList = new ArrayList<>();
    FragmentAddBinding binding;
    AddImageAdapter addImageAdapter;
    MainActivity mainActivity;
    private Uri imageUri;
    Context context;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null) {
                    imageUri = result.getData().getData();
                }
                bitmapList.set(positionAdd, imageUri);
                addImageAdapter.notifyDataSetChanged();
            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater);
        initView();
        setClick();
        return binding.getRoot();
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        context = App.context;
        bitmapList.clear();
        for (int i = 0; i < 6; i++) {
            bitmapList.add(null);

        }
//        addImageAdapter = new AddImageAdapter(bitmapList, context, this);
//        binding.recAddImage.setAdapter(addImageAdapter);
//        addImageAdapter.setData(bitmapList);
    }

    private void setClick() {
        binding.btnNext.setOnClickListener(v -> {
//            if (checkBitmap()) {
//            }
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
        if (bitmapList.size()<=2){
            Toast.makeText(mainActivity, "Bạn cần có tối thiểu 2 ảnh", Toast.LENGTH_SHORT).show();
        }
        else  {
            bitmapList.remove(position);
        }

    }
}