package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.StoryAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityStoryBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.model.StoryItem;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class StoryActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    public static List<StoryItem> list = new ArrayList<>();
    StoryAdapter storyAdapter;
    long pressTime = 0L;
    long limit = 500L;
    String userId ;
    UserProfile userProfile;
    ActivityStoryBinding binding;

    ProgressDialog progressDialog;
    private FirebaseDatabase database;

    List<String> uriList = new ArrayList<>();
    private int counter = 0;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    binding.stories.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    binding.stories.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        Intent intent = getIntent();
        if (intent.getSerializableExtra(Constants.KEY_USER) != null) {
            userProfile = (UserProfile) intent.getSerializableExtra(Constants.KEY_USER);
        }
        getImage();


    }

    private void getImage() {
        uriList.clear();
        if (userProfile!=null){
            progressDialog.show();
            DatabaseReference myRef = database.getReference("user_image/" + userProfile.getUserId());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        try {
                            String uri = dataSnapshot.getValue(String.class);
                            if (uri != null) {
                                uriList.add(uri);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "" + e);
                        }
                    }
                    Log.e(TAG, "ls" + uriList.size());
                    Log.e(TAG, "userProfile.getUserId()" + userProfile.getUserId());
                    startStory();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "profile list empty");
                }
            });
        }
        Glide.with(this).load(userProfile.getImageUri()).error(R.drawable.demo).centerCrop().into(binding.imgAvatar);
        binding.tvName.setText(userProfile.getName());

    }

    void startStory(){
        if (uriList.size()!=0){
            binding.stories.setStoriesCount(uriList.size()); // <- set stories
            binding.stories.setStoryDuration(5000L); // <- set a story duration
            binding.stories.setStoriesListener(this); // <- set listener
            binding.stories.startStories(counter); // <- start progress
            storyAdapter = new StoryAdapter(this, uriList);
            binding.viewPager.setAdapter(storyAdapter);
            binding.viewPager.setCurrentItem(counter);
        }
    }

    private void setClick() {
        binding.btnCancel.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        binding.reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.stories.reverse();
            }
        });
        binding.reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.stories.skip();
            }
        });
        binding.skip.setOnTouchListener(onTouchListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onNext() {
        if ((counter + 1) == list.size()) return;

        binding.viewPager.setCurrentItem(++counter);

    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        binding.viewPager.setCurrentItem(--counter);
    }

    @Override
    public void onComplete() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        // Very important !
        binding.stories.destroy();
        super.onDestroy();
    }

}