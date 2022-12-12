package com.ltmt5.fpoly_friend_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdpater extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public ViewPageAdpater(ArrayList<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position >= 0 && position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
