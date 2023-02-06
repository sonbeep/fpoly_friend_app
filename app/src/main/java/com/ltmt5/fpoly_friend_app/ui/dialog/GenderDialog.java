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

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.GenderAdapter;
import com.ltmt5.fpoly_friend_app.databinding.DialogGenderBinding;
import com.ltmt5.fpoly_friend_app.databinding.DialogUpdateProfileBinding;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class GenderDialog extends BaseDialogFragment implements GenderAdapter.ItemClick {
    DialogGenderBinding binding;
    OnClickListener onClickListener;
    GenderAdapter genderAdapter;
    String data;

    public GenderDialog() {
    }

    public static GenderDialog newInstance() {
        return new GenderDialog();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogGenderBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(App.context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        genderAdapter = new GenderAdapter(App.context, this);
        binding.rec.setLayoutManager(layoutManager);
        binding.rec.setAdapter(genderAdapter);
        genderAdapter.setData(getListGender());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCancel.setOnClickListener(v -> {
            onClickListener.onCancel();
            dismiss();
        });
        binding.btnApply.setOnClickListener(v -> {
            onClickListener.onApply(data);
            dismiss();
        });
    }

    List<Hobbies> getListGender() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Nam"));
        list.add(new Hobbies("Nữ"));
        list.add(new Hobbies("Khác"));
        return list;
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

    @Override
    public void clickGender(Hobbies hobbies) {
        data = hobbies.getName();
    }


    public interface OnClickListener {
        void onApply(String data);

        void onCancel();
    }
}


