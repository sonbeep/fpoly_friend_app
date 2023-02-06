package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;

public abstract class BaseFragment extends Fragment {
    protected MainActivity activity;

    protected MainActivity ensureHomeActivity() {

        if (activity == null) {
            activity = (MainActivity) getActivity();
        }
        return activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureHomeActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        ensureHomeActivity();
    }

}
