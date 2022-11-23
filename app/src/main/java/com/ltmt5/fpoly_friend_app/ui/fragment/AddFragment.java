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
    public static List<Bitmap> bitmapList = new ArrayList<>();
    FragmentAddBinding binding;
    AddImageAdapter addImageAdapter;
    File fileCamera;
    MainActivity mainActivity;
    private Uri cameraUri;
    Context context;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Bitmap bitmap = null;
                if (result.getData() == null) {
                    bitmap = mainActivity.getBitmapFromUri(cameraUri);
                } else {
                    Uri imageUri = result.getData().getData();
                    bitmap = mainActivity.getBitmapFromUri(imageUri);
                }
                bitmapList.set(positionAdd, bitmap);
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
        addImageAdapter = new AddImageAdapter(bitmapList, context, this);
        binding.recAddImage.setAdapter(addImageAdapter);
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
                launcher.launch(getIntentImage());
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        }).setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]").setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).check();

    }

    private Uri createCameraUri() {
        String nameTest = "camera_" + System.currentTimeMillis();
        try {
            fileCamera = File.createTempFile(nameTest, ".png");
        } catch (IOException e) {
            return null;
        }
        return FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", fileCamera);
    }

    public Intent getIntentImage() {
        cameraUri = createCameraUri();
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        pickIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.putExtra(Intent.EXTRA_INDEX, 0);


        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        return chooserIntent;
    }

    @Override
    public void deleteItem(int position) {
        bitmapList.remove(position);
    }

    boolean checkBitmap() {
        boolean isDone = true;
        int count = 0;
        for (Bitmap bitmap : bitmapList) {
            if (bitmap != null) {
                count++;
            }
        }
        if (count < 2) {
            Toast.makeText(getActivity(), "Vui lòng chọn ít nhất 2 ảnh", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }
}