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
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.DialogGenderBinding;
import com.ltmt5.fpoly_friend_app.databinding.DialogUpdateProfileBinding;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class EducationDialog extends BaseDialogFragment implements GenderAdapter.ItemClick {
    DialogGenderBinding binding;
    OnClickListener onClickListener;
    GenderAdapter genderAdapter;
    String data;

    public EducationDialog() {
    }

    public static EducationDialog newInstance() {
        return new EducationDialog();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
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
        genderAdapter.setData(getListEducation());
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

    List<Hobbies> getListEducation() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Phát triển phần mềm"));
        list.add(new Hobbies("Lập trình Web"));
        list.add(new Hobbies("Lập trình Mobile"));
        list.add(new Hobbies("Ứng dụng phần mềm"));
        list.add(new Hobbies("Xử lý dữ liệu"));
        list.add(new Hobbies("Digital Marketing"));
        list.add(new Hobbies("Marketing & Sale"));
        list.add(new Hobbies("Quan hệ công chúng (PR) & Tổ chức sự kiện"));
        list.add(new Hobbies("Quản trị Khách sạn"));
        list.add(new Hobbies("Quản trị Nhà hàng"));
        list.add(new Hobbies("Logistic"));
        list.add(new Hobbies("Công nghệ kỹ thuật điều khiển & Tự động hoá"));
        list.add(new Hobbies("Công nghệ kỹ thuật điện, điện tử"));
        list.add(new Hobbies("Điện công nghiệp"));
        list.add(new Hobbies("Thiết kế đồ họa"));
        list.add(new Hobbies("Hướng dẫn du lịch"));
        list.add(new Hobbies("Công nghệ kỹ thuật cơ khí "));
        list.add(new Hobbies("Chăm sóc da và Spa"));
        list.add(new Hobbies("Trang điểm nghệ thuật"));
        list.add(new Hobbies("Phun thêu thẩm mỹ"));
        list.add(new Hobbies("Công nghệ móng"));
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


