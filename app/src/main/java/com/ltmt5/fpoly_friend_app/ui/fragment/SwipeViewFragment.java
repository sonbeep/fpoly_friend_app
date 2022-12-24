package com.ltmt5.fpoly_friend_app.ui.fragment;

import static com.ltmt5.fpoly_friend_app.App.TAG;
import static com.ltmt5.fpoly_friend_app.App.currentUser;
import static com.ltmt5.fpoly_friend_app.App.user;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.FragmentSwipeViewBinding;
import com.ltmt5.fpoly_friend_app.help.UtilsMode;
import com.ltmt5.fpoly_friend_app.model.TinderCard;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.ProfileActivity;
import com.mindorks.placeholderview.SwipeDecor;

import java.util.ArrayList;
import java.util.List;

public class SwipeViewFragment extends Fragment {
    public static final String EXTRA_USER_PROFILE = "EXTRA_USER_PROFILE";
    public static final String EXTRA_SWIPE_VIEW_SOURCE = "EXTRA_SWIPE_VIEW_SOURCE";
    public static UserProfile mProfile;
    public static List<UserProfile> userProfileList = new ArrayList<>();
    public static int mPos = 0;
    FragmentSwipeViewBinding binding;
    MainActivity mainActivity;
    public static FirebaseDatabase database;
    private Context mContext;

    public SwipeViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSwipeViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setClick();
    }

    private void setClick() {
        binding.btnSkip.setOnClickListener(v -> {
            animateFab(binding.btnSkip);
            binding.swipeView.doSwipe(false);
        });

        binding.btnLike.setOnClickListener(v -> {
            handleLikeClick();
            animateFab(binding.btnLike);
            binding.swipeView.doSwipe(true);
        });


        binding.btnBoost.setOnClickListener(v -> animateFab(binding.btnBoost));
        binding.btnStar.setOnClickListener(v -> animateFab(binding.btnStar));
        binding.btnReverse.setOnClickListener(v -> animateFab(binding.btnReverse));

        binding.btnInfo.setOnClickListener(v -> {
            mainActivity.loadProfileActivity();
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(mainActivity,
                            Pair.create(binding.swipeView, "user_swipe_image_transition"));
            if (mPos < 0 || mPos > userProfileList.size() - 1) {
                mPos = userProfileList.size() - 1;
            }
            mProfile = userProfileList.get(mPos);
            UserProfile userProfile = new UserProfile(mProfile.getUserId(), mProfile.getName(), mProfile.getAge(), mProfile.getGender(), mProfile.getEducation(), mProfile.getHobbies(), mProfile.getImageUri());
            mainActivity.startActivity(getBundledIntent(userProfile), options.toBundle());
        });
    }

    private void handleLikeClick() {
        if (mPos < 0 || mPos > userProfileList.size() - 1) {
            mPos = userProfileList.size() - 1;
        }
        mProfile = userProfileList.get(mPos);
        DatabaseReference myRef = database.getReference("user_profile_match/" + user.getUid() + "/" + mProfile.getUserId());
        myRef.setValue(mProfile, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.e(TAG, "created user profile");
                DatabaseReference myRef2 = database.getReference("user_profile/" + user.getUid() + "/match");
                myRef2.setValue((currentUser.getMatch() + 1), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Log.e(TAG, "update match");
                    }
                });
            }
        });

    }

    private Intent getBundledIntent(UserProfile profile) {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra(EXTRA_USER_PROFILE, profile);
        intent.putExtra(EXTRA_SWIPE_VIEW_SOURCE, true);
        return intent;
    }

    private void initView() {
        mContext = App.context;
        database = FirebaseDatabase.getInstance();
        mainActivity = (MainActivity) getActivity();
        int bottomMargin = UtilsMode.dpToPx(0);
        Point windowSize = UtilsMode.getDisplaySize(getActivity().getWindowManager());
        binding.swipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));
        userProfileList = App.userProfileList;
        Log.e(TAG, "ls app:" + userProfileList.size());
        for (int i = 0; i < userProfileList.size(); i++) {
            binding.swipeView.addView(new TinderCard(mContext, userProfileList.get(i), binding.swipeView, i));
        }
    }

    private void animateFab(final View fab) {
        fab.animate().scaleX(0.7f).setDuration(100).withEndAction(() -> fab.animate().scaleX(1f).scaleY(1f));
    }

    public static void match(){
        Log.e(TAG,"match");
        if (mPos < 0 || mPos > userProfileList.size() - 1) {
            mPos = userProfileList.size() - 1;
        }
        mProfile = userProfileList.get(mPos);
        DatabaseReference myRef = database.getReference("user_profile_match/" + user.getUid() + "/" + mProfile.getUserId());
        myRef.setValue(mProfile, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.e(TAG, "created user profile");
                DatabaseReference myRef2 = database.getReference("user_profile/" + user.getUid() + "/match");
                myRef2.setValue((currentUser.getMatch() + 1), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Log.e(TAG, "update match");
                    }
                });
            }
        });
    }

    public static void unMatch(){
        Log.e(TAG,"unMatch");
    }

}