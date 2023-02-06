package com.ltmt5.fpoly_friend_app.ui.fragment;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.FragmentUserBinding;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.SettingActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.UpdateProfileActivity;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;
    MainActivity mainActivity;
    Context context;

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
        try {
            binding.tvName.setText(App.currentUser.getName() + " " + (2022 - App.currentUser.getAge()));
            Glide.with(context).load(App.currentUser.getImageUri()).error(R.drawable.demo).centerCrop().into(binding.profileImage);
        } catch (Exception e) {
            Log.e(TAG, "initView: " + e);
        }

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