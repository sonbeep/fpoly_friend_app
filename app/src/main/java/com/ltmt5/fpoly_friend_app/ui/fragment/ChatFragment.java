package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.ChatAdapter;
import com.ltmt5.fpoly_friend_app.adapter.RecentlyAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentChatBinding;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements ChatAdapter.ItemClick, RecentlyAdapter.ItemClick {
    FragmentChatBinding binding;
    MainActivity mainActivity;
    Context context;
    ChatAdapter chatAdapter;
    RecentlyAdapter recentlyAdapter;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater);
        initalizeView();
        setClick();
        return binding.getRoot();
    }

    private void initalizeView() {
        context = App.context;
        mainActivity = (MainActivity) getActivity();
        chatAdapter = new ChatAdapter(getList(), context, this);
        binding.recChat.setAdapter(chatAdapter);

        recentlyAdapter = new RecentlyAdapter(getList(), context, this);
        binding.recRecently.setAdapter(recentlyAdapter);
    }

    private void setClick() {

    }

    private List<Chat> getList() {
        List<Chat> list = new ArrayList<>();
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        return list;
    }

    @Override
    public void clickItem(Chat chat) {

    }
}