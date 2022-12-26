package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion4Binding;
import com.ltmt5.fpoly_friend_app.help.PublicData;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class Question4Activity extends AppCompatActivity implements HobbiesAdapter.ItemClick {
    ActivityQuestion4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        binding.btnNext.setOnClickListener(v -> {
//            if (validate()) {
            PublicData.profileTemp.setEducation(mEdu);
            startActivity(new Intent(this, Question5Activity.class));
//            }
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }
    HobbiesAdapter hobbiesAdapter;
    String mEdu = "Trống";

    private void initView() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(this,this);
        binding.recHobbies.setLayoutManager(layoutManager);
        binding.recHobbies.setAdapter(hobbiesAdapter);
        hobbiesAdapter.setData(getListEducation(), "education");
        mEdu = getListEducation().get(0).getName();

    }

//    boolean validate() {
//        boolean isDone = true;
//        if (binding.ed1.getText().toString().trim().equals("")) {
//            Toast.makeText(this, "Chuyên ngành không được để trống", Toast.LENGTH_SHORT).show();
//            isDone = false;
//        }
//        return isDone;
//    }

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
        list.get(0).setSelected(true);
        return list;
    }

    @Override
    public void clickItem(Hobbies hobbies) {
        mEdu = hobbies.getName();
    }
}