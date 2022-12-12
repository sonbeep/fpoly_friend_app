package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    String phoneNumber;
    String id;
    SwipeViewFragment swipeViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDataIntent();
        intitView();
        getUserInfo();
    }

    private void intitView() {
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        swipeViewFragment = new SwipeViewFragment();
        getUserProfile();

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(swipeViewFragment);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.e(TAG, "token: " + token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        checkList();
    }

    public void checkList() {
        if (App.userProfileList == null) {
            App.userProfileList = new ArrayList<>();
            getAllUserProfile();
        } else {
            if (App.userProfileList.size() == 0) {
                getAllUserProfile();
            }
        }
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

    public Bitmap getBitmapFromUri(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
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

    public void getUserProfile() {
        DatabaseReference myRef = database.getReference("user_profile/" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    userProfile = snapshot.getValue(UserProfile.class);
                } catch (Exception e) {
                    Log.e(TAG, "" + e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllUserProfile() {
        App.userProfileList = new ArrayList<>();
        List<UserProfile> userProfileList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_profile/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        userProfileList.add(userProfile);
                    }
                    App.userProfileList.addAll(userProfileList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "profile list empty");
            }
        });
    }


    public String convertBitmapToArray(Bitmap bitmap) {
        int previewWith = 150;
        int previewHeight = bitmap.getHeight() * previewWith / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWith, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap getBitmapFromArray(String encoded) {
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


}