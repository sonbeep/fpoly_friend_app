package com.ltmt5.fpoly_friend_app.ui.activity;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityMainBinding;
import com.ltmt5.fpoly_friend_app.ui.fragment.AddFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.ChatFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.HomeFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.LoveFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.SwipeViewFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new SwipeViewFragment());
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new SwipeViewFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_love:
                fragment = new LoveFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_add:
                fragment = new AddFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_chat:
                fragment = new ChatFragment();
                loadFragment(fragment);
                return true;
            case R.id.navigation_user:
                fragment = new UserFragment();
                loadFragment(fragment);
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.fcvMain.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private FragmentActivity fragmentActivity;

    public void loadProfileActivity(){

    }

}