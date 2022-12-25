package com.ltmt5.fpoly_friend_app.ui.dialog;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class BaseDialogFragment extends DialogFragment {

    public boolean mStateSaved;


    @CallSuper
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mStateSaved = true;
        super.onSaveInstanceState(outState);
    }

    /**
     * Version of {@link #show(FragmentManager, String)} that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        // API 26 added this convenient method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.isStateSaved()) {
                return;
            }
        }

        if (mStateSaved) {
            return;
        }

        show(manager, tag);
    }
}
