package com.ltmt5.fpoly_friend_app.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.ui.fragment.SwipeViewFragment;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;


@Layout(R.layout.adapter_tinder_card)
public class TinderCard {

    @View(R.id.profileImageView)
    public ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    public TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    public TextView locationNameTxt;

    public UserProfile mProfile;
    public Context mContext;
    public SwipePlaceHolderView mSwipeView;
    Bitmap bitmap;
    int pos;

    public TinderCard(Context context, UserProfile profile, SwipePlaceHolderView swipeView, int mPos) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        this.pos = mPos;
    }

    @Resolve
    public void onResolved() {
        if (mProfile != null) {
            Glide.with(mContext).load(mProfile.getImageUri()).centerCrop().error(R.drawable.demo1).into(profileImageView);
            nameAgeTxt.setText(mProfile.getName() + ", " + (2022 - mProfile.getAge()));
            locationNameTxt.setText(mProfile.getEducation());
//            SwipeViewFragment.mProfile = mProfile;
        }

    }

    @SwipeOut
    public void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
        SwipeViewFragment.mPos = pos+1;
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
        mSwipeView.addView(this);
        SwipeViewFragment.mPos = pos + 1;
    }


    @SwipeInState
    public void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }

}