package com.ltmt5.fpoly_friend_app.ui.fragment;

import static com.ltmt5.fpoly_friend_app.App.TAG;
import static com.ltmt5.fpoly_friend_app.App.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.FilterLoveAdapter;
import com.ltmt5.fpoly_friend_app.adapter.LoveAdapter;
import com.ltmt5.fpoly_friend_app.adapter.RecomentAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentLoveBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.model.Hobbies;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.activity.ViewProfileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoveFragment extends BaseFragment implements FilterLoveAdapter.ItemClick, LoveAdapter.ItemClick, RecomentAdapter.ItemClick {
    FragmentLoveBinding binding;
    LoveAdapter loveAdapter;
    LoveAdapter loveAdapterFind;
    FirebaseDatabase database;
    RecomentAdapter recomentAdapter;

    List<UserProfile> userProfileList = new ArrayList<>();
    List<UserProfile> userListMain = new ArrayList<>();
    List<UserProfile> userListFind = new ArrayList<>();
    List<UserProfile> recommendList;
    UserProfile mUserProfile;
    boolean isFirst;

    public static LoveFragment newInstance() {
        return new LoveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoveBinding.inflate(inflater);
        initView();
        setClick();
        setUpSearchView();
        getListRecommend();
        return binding.getRoot();
    }

    private void getListRecommend() {
        ensureHomeActivity().checkList();
        mUserProfile = App.currentUser;
        recommendList = new ArrayList<>();
        for (UserProfile userProfile : App.userProfileList) {
            if (userProfile.getAge() == mUserProfile.getAge() || userProfile.getEducation().equals(mUserProfile.getEducation())) {
                recommendList.add(userProfile);
            }
            if (userProfile.getLocation() != null && mUserProfile.getLocation() != null) {
                if (userProfile.getLocation().equals(mUserProfile.getLocation())) {
                    recommendList.add(userProfile);
                }
            }
        }
        loveAdapterFind.setData(recommendList);
    }

    private void setUpSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userListFind.clear();
                for (UserProfile userProfile : userListMain) {
//                    if (userProfile.getHobbies() != null) {
//                        for (String s : userProfile.getHobbies()) {
//                            if (s.contains(query)) {
//                                if (!userListFind.contains(userProfile)) {
//                                    userListFind.add(userProfile);
//                                }
//                            }
//                        }
//                    }
                    if (userProfile.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                        userListFind.add(userProfile);
                    }
                }
                loveAdapterFind.setData(userListFind);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        ensureHomeActivity().checkList();

        loveAdapter = new LoveAdapter(App.context, this);
        binding.recLove.setAdapter(loveAdapter);

        loveAdapterFind = new LoveAdapter(App.context, this);
        binding.recFindResult.setAdapter(loveAdapterFind);

        recomentAdapter = new RecomentAdapter(App.context, this);
        binding.recFind.setAdapter(recomentAdapter);
        recomentAdapter.setData(getList());

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userListMain.addAll(App.userProfileList);

        getListMatch();
    }

    private void getListMatch() {
        isFirst = true;
        DatabaseReference myRef = database.getReference("user_profile_match/" + user.getUid() + "/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isFirst) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot != null) {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (userProfile != null) {
                                if (userProfile.getAvailability() == -2)
                                    userProfileList.add(userProfile);
                            }
                        }
                    }
                    loveAdapter.setData(userProfileList);
                    binding.tvTitle.setText("Bạn đã nhận được " + userProfileList.size() + " lượt thích !");
                    isFirst = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }

    private void setClick() {
        binding.cvFind.setOnClickListener(view -> {
            binding.cvFind.setCardBackgroundColor(ContextCompat.getColor(ensureHomeActivity(), R.color.prime_1));
            binding.cvFind.setStrokeWidth(0);
            binding.tvFind.setTextColor(ContextCompat.getColor(ensureHomeActivity(), R.color.white));

            binding.cvMatch.setCardBackgroundColor(ContextCompat.getColor(ensureHomeActivity(), R.color.transparent));
            binding.cvMatch.setStrokeWidth(3);
            binding.tvMatch.setTextColor(ContextCompat.getColor(ensureHomeActivity(), R.color.black));

            binding.layoutFind.setVisibility(View.VISIBLE);
            binding.layoutMatch.setVisibility(View.GONE);
        });

        binding.cvMatch.setOnClickListener(view -> {
            binding.cvMatch.setCardBackgroundColor(ContextCompat.getColor(ensureHomeActivity(), R.color.prime_1));
            binding.cvMatch.setStrokeWidth(0);
            binding.tvMatch.setTextColor(ContextCompat.getColor(ensureHomeActivity(), R.color.white));

            binding.cvFind.setCardBackgroundColor(ContextCompat.getColor(ensureHomeActivity(), R.color.transparent));
            binding.cvFind.setStrokeWidth(3);
            binding.tvFind.setTextColor(ContextCompat.getColor(ensureHomeActivity(), R.color.black));

            binding.layoutFind.setVisibility(View.GONE);
            binding.layoutMatch.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void clickItem(UserProfile userProfile) {
        startActivity(new Intent(getActivity(), ViewProfileActivity.class).putExtra(Constants.KEY_USER, userProfile));
    }

    @Override
    public void clickItem(Chat chat) {

    }

    List<Hobbies> getList() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Đề xuất"));
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
        list.get(0).setSelected(true);
        return list;
    }

    @Override
    public void clickRecommend(Hobbies hobbies) {
        if (hobbies.getName().equals("Đề xuất")) {
            getListRecommend();
        } else {
            userListFind.clear();
            for (UserProfile userProfile : userListMain) {
                if (userProfile.getHobbies() != null) {
                    for (String s : userProfile.getHobbies()) {
                        if (hobbies.getName().equals(s)) {
                            userListFind.add(userProfile);
                        }
                    }
                }
            }
            loveAdapterFind.setData(userListFind);
        }

    }
}