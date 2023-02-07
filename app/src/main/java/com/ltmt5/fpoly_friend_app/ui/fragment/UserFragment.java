package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.FragmentUserBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.SettingActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.UpdateProfileActivity;
import com.ltmt5.fpoly_friend_app.ui.dialog.UpdateDialog;

public class UserFragment extends BaseFragment {
    FragmentUserBinding binding;
    MainActivity mainActivity;
    Context context;
    UserProfile mUserProfile;

    public UserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater);
        mainActivity = (MainActivity) getActivity();
        context = App.context;
        setClick();
        initView();
        return binding.getRoot();
    }

    private void initView() {
        mUserProfile = App.currentUser;
        binding.tvName.setText(mUserProfile.getName() + " " + (2022 - mUserProfile.getAge()));
        Glide.with(context).load(mUserProfile.getImageUri()).error(R.drawable.demo).centerCrop().into(binding.profileImage);


    }

    private void openDialog() {
        UpdateDialog updateDialog = UpdateDialog.newInstance();
        updateDialog.showAllowingStateLoss(ensureHomeActivity().getSupportFragmentManager(), "UpdateDialog");
        updateDialog.setOnClickListener(new UpdateDialog.OnClickListener() {
            @Override
            public void onApply() {
                startActivity(new Intent(ensureHomeActivity(), UpdateProfileActivity.class));
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void setClick() {
        binding.tvVIP.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
        });

        binding.btnAdd.setOnClickListener(view -> {
            mainActivity.loadFragment(new AddFragment(), R.id.navigation_add);
        });

        binding.btnUpdate.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
        });

        binding.btnSetting.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
    }
}