package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.FilterLoveAdapter;
import com.ltmt5.fpoly_friend_app.adapter.LoveAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentLoveBinding;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoveFragment extends Fragment implements FilterLoveAdapter.ItemClick, LoveAdapter.ItemClick {
    FragmentLoveBinding binding;
    MainActivity mainActivity;
    Context context;
    FilterLoveAdapter filterLoveAdapter;
    LoveAdapter loveAdapter;

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
        mainActivity = (MainActivity) getActivity();
        filterLoveAdapter = new FilterLoveAdapter(getList(), context, this);
        binding.recFilter.setAdapter(filterLoveAdapter);

        loveAdapter = new LoveAdapter(getList(), context, this);
        binding.recLove.setAdapter(loveAdapter);
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
    public void clickItem(Chat chat) {

    }
}