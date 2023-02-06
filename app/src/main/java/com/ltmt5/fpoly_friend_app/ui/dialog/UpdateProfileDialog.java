package com.ltmt5.fpoly_friend_app.ui.dialog;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.DialogUpdateProfileBinding;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileDialog extends BaseDialogFragment implements HobbiesAdapter.ItemClick {
    DialogUpdateProfileBinding binding;
    OnClickListener onClickListener;
    String name = "name";
    String data = "none";
    HobbiesAdapter hobbiesAdapter;
    List<Hobbies> list;
    List<String> stringList;

    public UpdateProfileDialog() {
    }

    public UpdateProfileDialog(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public static UpdateProfileDialog newInstance(String name, String data) {
        return new UpdateProfileDialog(name, data);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        binding = DialogUpdateProfileBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        list = new ArrayList<>();
        stringList = new ArrayList<>();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(App.context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(App.context, this);
        binding.rec.setLayoutManager(layoutManager);
        binding.rec.setAdapter(hobbiesAdapter);
        switch (name) {
            case "age":
                binding.tv1.setText("Năm sinh");
                binding.rec.setVisibility(View.GONE);
                binding.ed1.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "gender":
                binding.tv1.setText("Giới tính");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListGender(), data);
                list.add(new Hobbies(data));
                break;
            case "education":
                binding.tv1.setText("Chuyên ngành");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListEducation(), data);
                list.add(new Hobbies(data));
                break;
            case "hobbies":
                binding.tv1.setText("Sở thích");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getHoobies(), data);
                hobbiesAdapter.setMultiple(true);
                list.add(new Hobbies(data));
                break;
            case "description":
                binding.tv1.setText("Mô tả");
                binding.rec.setVisibility(View.GONE);
                break;
            case "location":
                binding.tv1.setText("Địa chỉ");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListLocation(), data);
                list.add(new Hobbies(data));
                break;
            case "zodiac":
                binding.tv1.setText("Cung hoàng đạo");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListZodiac(), data);
                list.add(new Hobbies(data));
                break;
            case "personality":
                binding.tv1.setText("Tính cách");
                binding.rec.setVisibility(View.GONE);
                break;
            case "favoriteSong":
                binding.tv1.setText("Bài hát yêu thích");
                binding.rec.setVisibility(View.GONE);
                break;
            case "sexualOrientation":
                binding.tv1.setText("Xu hướng tính dục");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListSexualOrientation(), data);
                list.add(new Hobbies(data));
                break;
            case "showPriority":
                binding.tv1.setText("Ưu tiên hiển thị");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListShowPriority(), data);
                list.add(new Hobbies(data));
                break;
            case "name":
            default:
                binding.tv1.setText("Họ tên");
                binding.rec.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCancel.setOnClickListener(v -> {
            onClickListener.onCancel();
            dismiss();
        });
        binding.btnApply.setOnClickListener(v -> {
            String data = null;
            if (!name.equals("hobbies")) {
                if (name.equals("location")||name.equals("gender") || name.equals("education") || name.equals("sexualOrientation") || name.equals("zodiac") || name.equals("showPriority")) {
                    if (list.size() != 0) {
                        data = list.get(0).getName();
                    }
                } else {
                    data = binding.ed1.getText().toString().trim();
                }
            }
            for (Hobbies hobbies : list) {
                stringList.add(hobbies.getName());
            }
            if (validate()) {
                onClickListener.onApply(data, stringList);
                dismiss();
            }
        });
    }

    private boolean validate() {
        boolean isDone = true;
        switch (name) {
            case "age":
                if (binding.ed1.getText().toString().toString().equals("")) {
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    isDone = false;
                }
                else {
                    int age = Integer.parseInt(binding.ed1.getText().toString().trim());
                    if (age < 1970 || age > 2022) {
                        Toast.makeText(getActivity(), "Năm không hợp lệ", Toast.LENGTH_SHORT).show();
                        isDone = false;
                    }
                }

                break;
            case "description":
            case "personality":
            case "favoriteSong":
            case "name":
                if (binding.ed1.getText().toString().toString().equals("")) {
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    isDone = false;
                }
                break;
            default:
                binding.tv1.setText("Họ tên");
                binding.rec.setVisibility(View.GONE);
                break;

        }
        return isDone;
    }

    List<Hobbies> getListGender() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Nam"));
        list.add(new Hobbies("Nữ"));
        list.add(new Hobbies("Khác"));
        return list;
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

    List<Hobbies> getHoobies() {
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

    List<Hobbies> getListZodiac() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Cung Bảo Bình"));
        list.add(new Hobbies("Cung Song Ngư"));
        list.add(new Hobbies("Cung Bạch Dương"));
        list.add(new Hobbies("Cung Kim Ngưu"));
        list.add(new Hobbies("Cung Song Tử"));
        list.add(new Hobbies("Cung Cự Giải"));
        list.add(new Hobbies("Cung Sư Tử"));
        list.add(new Hobbies("Cung Xử Nữ"));
        list.add(new Hobbies("Cung Thiên Bình"));
        list.add(new Hobbies("Cung Bọ Cạp"));
        list.add(new Hobbies("Cung Nhân Mã"));
        list.add(new Hobbies("Cung Ma Kết"));
        return list;
    }

    List<Hobbies> getListSexualOrientation() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Dân thường"));
        list.add(new Hobbies("Dị tính"));
        list.add(new Hobbies("Đồng tính"));
        list.add(new Hobbies("Chuyển giới"));
        list.add(new Hobbies("Song tính"));
        list.add(new Hobbies("Vô tính"));
        list.add(new Hobbies("Toàn tính"));
        list.add(new Hobbies("Ái tính"));
        list.add(new Hobbies("Khác"));
        return list;
    }

    List<Hobbies> getListShowPriority() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Nam"));
        list.add(new Hobbies("Nữ"));
        list.add(new Hobbies("Khác"));
        list.add(new Hobbies("Tất cả"));
        return list;
    }

    List<Hobbies> getListLocation() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Hà Nội"));
        list.add(new Hobbies("Bắc Giang"));
        list.add(new Hobbies("Hải Phòng"));
        list.add(new Hobbies("TP.HCM"));
        list.add(new Hobbies("Đà Nẵng"));
        list.add(new Hobbies("Huế"));
        list.add(new Hobbies("Đồng Nai"));
        list.add(new Hobbies("Cần Thơ"));
        list.add(new Hobbies("Tây Nguyên"));
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
    public void clickItem(Hobbies hobbies) {
        if (!name.equals("hobbies")) {
            list.clear();
            list.add(hobbies);
        } else {
            if (hobbies.isSelected()) {
                list.add(hobbies);
            } else {
                list.remove(hobbies);
            }
        }
    }


    public interface OnClickListener {
        void onApply(String data, List<String> list);

        void onCancel();
    }
}


