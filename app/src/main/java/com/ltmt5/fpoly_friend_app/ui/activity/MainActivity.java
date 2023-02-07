package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ActivityMainBinding;
import com.ltmt5.fpoly_friend_app.model.Profile;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.fragment.AddFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.ChatFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.LoveFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.SwipeViewFragment;
import com.ltmt5.fpoly_friend_app.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<UserProfile> userProfiles = new ArrayList<>();
    public Profile profile;
    public UserProfile userProfile;
    ActivityMainBinding binding;
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
    FirebaseDatabase database;
    FirebaseUser user;
    SwipeViewFragment swipeViewFragment;
    boolean isFirst;

    public void checkList() {
        if (App.userProfileList == null || App.userProfileList.size() == 0) {
            getUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intitView();
        Log.e(TAG, "Load main");
    }

    private void intitView() {
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        swipeViewFragment = new SwipeViewFragment();
        if (App.userProfileList.size() == 0) {
            getUser();
        }

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(swipeViewFragment);
    }

    private void getUser() {
        isFirst = true;
        DatabaseReference myRef = database.getReference("user_profile/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isFirst) {
                    App.userProfileList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        try {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (userProfile != null) {
                                App.userProfileList.add(userProfile);
                            }
                        } catch (Exception e) {
                            Log.e("AAA", "get user error" + e);
                        }
                    }
                    Log.e(TAG, "main - list profile size: " + App.userProfileList.size());
                    isFirst = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }


    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.fcvMain.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadFragment(Fragment fragment, int id) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.fcvMain.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        binding.navigation.setSelectedItemId(id);
    }

    public void loadProfileActivity() {

    }

    private void getUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.e(TAG, "null");
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if (photoUrl != null) {
            profile = new Profile(name, photoUrl.toString(), email);
        } else {
            profile = new Profile(name, null, email);
        }
    }

    public void finish() {
        finish();
    }


}