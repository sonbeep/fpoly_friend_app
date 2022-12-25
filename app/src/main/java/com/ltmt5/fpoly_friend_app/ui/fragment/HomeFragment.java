package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ltmt5.fpoly_friend_app.adapter.SwipeAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentHomeBinding;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    MainActivity mainActivity;
    Context context;

    SwipeAdapter swipeAdapter;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);
        initalizeView();
        setClick();
        return binding.getRoot();
    }

    private void initalizeView() {
        context = App.context;
        mainActivity = (MainActivity) getActivity();
        swipeAdapter = new SwipeAdapter(context, getList());
        binding.koloda.setAdapter(swipeAdapter);
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

}