package com.ltmt5.fpoly_friend_app.ui.fragment;

import static com.ltmt5.fpoly_friend_app.App.TAG;
import static com.ltmt5.fpoly_friend_app.App.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.FilterLoveAdapter;
import com.ltmt5.fpoly_friend_app.adapter.LoveAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentLoveBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.activity.ChatActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoveFragment extends Fragment implements FilterLoveAdapter.ItemClick, LoveAdapter.ItemClick {
    FragmentLoveBinding binding;
    MainActivity mainActivity;
    Context context;
    FilterLoveAdapter filterLoveAdapter;
    LoveAdapter loveAdapter;
    FirebaseDatabase database;
    List<UserProfile> userProfileList = new ArrayList<>();
    private PreferenceManager preferenceManager;

    public static LoveFragment newInstance() {
        return new LoveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoveBinding.inflate(inflater);
        initalizeView();
        setClick();
        return binding.getRoot();
    }

    private void initalizeView() {
        context = App.context;
        database = FirebaseDatabase.getInstance();
        mainActivity = (MainActivity) getActivity();
        filterLoveAdapter = new FilterLoveAdapter(getList(), context, this);
        binding.recFilter.setAdapter(filterLoveAdapter);

        loveAdapter = new LoveAdapter(context, this);
        binding.recLove.setAdapter(loveAdapter);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_profile_match/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        userProfileList.add(userProfile);
                    }
                }
                loveAdapter.setData(userProfileList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }

    private void setClick() {

    }

    private List<Chat> getList() {
        List<Chat> list = new ArrayList<>();
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        list.add(new Chat("img_love", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "18"));
        return list;
    }

    @Override
    public void clickItem(UserProfile userProfile) {
        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(Constants.KEY_USER, userProfile));
    }

    @Override
    public void clickItem(Chat chat) {

    }
}