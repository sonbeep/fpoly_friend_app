package com.ltmt5.fpoly_friend_app.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion5Binding;
import com.ltmt5.fpoly_friend_app.help.PublicData;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class Question5Activity extends AppCompatActivity implements HobbiesAdapter.ItemClick {
    ActivityQuestion5Binding binding;
    HobbiesAdapter hobbiesAdapter;
    List<Hobbies> hobbiesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
        binding.btnNext.setOnClickListener(v -> {
//            if (validate()) {
            List<String> list = new ArrayList<>();
            for (Hobbies hobbies : hobbiesList) {
                list.add(hobbies.getName());
            }
            PublicData.profileTemp.setHobbies(list);
            startActivity(new Intent(this, WelcomeActivity.class));
//            }
        });
    }

    private void initView() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(this, this);
        hobbiesAdapter.setData(getList());
        binding.recHobbies.setLayoutManager(layoutManager);
        binding.recHobbies.setAdapter(hobbiesAdapter);
    }

    private void setClick() {
    }

    List<Hobbies> getList() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Thế hệ 9x"));
        list.add(new Hobbies("Harry Potter"));
        list.add(new Hobbies("SoundCloud"));
        list.add(new Hobbies("Spa"));
        list.add(new Hobbies("Chăm sóc bản thân"));
        list.add(new Hobbies("Heavy Metal"));
        list.add(new Hobbies("Tiệc gia đình"));
        list.add(new Hobbies("Gin Toxic"));
        list.add(new Hobbies("Thể dục dụng cụ"));
        list.add(new Hobbies("Hot Yoga"));
        list.add(new Hobbies("Thiền"));
        list.add(new Hobbies("Sushi"));
        list.add(new Hobbies("Spotify"));
        list.add(new Hobbies("Hockey"));
        list.add(new Hobbies("Bóng rổ"));
        list.add(new Hobbies("Đấu thơ"));
        list.add(new Hobbies("Tập luyện tại nhà"));
        list.add(new Hobbies("Nhà hát"));
        list.add(new Hobbies("Khám phá quán cà phê"));
        list.add(new Hobbies("Thuỷ cung"));
        list.add(new Hobbies("Giày sneaker"));
        list.add(new Hobbies("Instagram"));
        list.add(new Hobbies("Suối nước nóng"));
        list.add(new Hobbies("Đi dạo"));
        list.add(new Hobbies("Chạy bộ"));
        list.add(new Hobbies("Du lịch"));
        list.add(new Hobbies("Giao lưu ngôn ngữ"));
        list.add(new Hobbies("Phim ảnh"));
        list.add(new Hobbies("Chơi guitar"));
        list.add(new Hobbies("Phát triển xã hội"));
        list.add(new Hobbies("Tập gym"));
        list.add(new Hobbies("Mạng xã hội"));
        list.add(new Hobbies("Hip-hop"));
        list.add(new Hobbies("Chăm sóc da"));
        list.add(new Hobbies("J-pop"));
        list.add(new Hobbies("Shisha"));
        list.add(new Hobbies("Cricket"));
        list.add(new Hobbies("Phim truyền hình Hàn Quốc"));
        return list;
    }

    boolean validate() {
        boolean isDone = true;
        if (hobbiesList.size() < 5) {
            Toast.makeText(this, "Bạn phải chọn ít nhất 5 sở thích", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void clickItem(Hobbies hobbies) {
        hobbies.setSelected(!hobbies.isSelected());
        hobbiesAdapter.notifyDataSetChanged();
        if (hobbies.isSelected()) {
            hobbiesList.add(hobbies);
        } else {
            hobbiesList.remove(hobbies);
        }
        if (hobbiesList.size() >= 5) {
            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
            binding.tvNext.setText("Tiếp tục 5/5");
        } else {
            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_4));
            binding.tvNext.setText("Tiếp tục " + hobbiesList.size() + "/5");
        }

    }
}