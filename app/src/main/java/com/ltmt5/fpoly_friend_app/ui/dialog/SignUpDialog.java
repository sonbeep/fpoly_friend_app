package com.ltmt5.fpoly_friend_app.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ltmt5.fpoly_friend_app.databinding.DialogSignUpBinding;

public class SignUpDialog extends BaseDialogFragment {
    DialogSignUpBinding binding;
    OnClickListener onClickListener;

    public SignUpDialog() {
    }

    public static SignUpDialog newInstance() {
        return new SignUpDialog();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        binding = DialogSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCancel.setOnClickListener(v -> {
            onClickListener.onCancel();
            dismiss();
        });
        binding.btnApply.setOnClickListener(v -> {
            onClickListener.onApply();
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCancelable(true);
        }
    }


    public interface OnClickListener {
        void onApply();

        void onCancel();
    }
}


