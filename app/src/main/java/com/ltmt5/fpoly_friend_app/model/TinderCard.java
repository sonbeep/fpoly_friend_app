package com.ltmt5.fpoly_friend_app.model;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;


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

    public TinderCard(Context context, UserProfile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(mProfile.getAvt()).centerCrop().into(profileImageView);
        nameAgeTxt.setText(mProfile.getName() + ", " + (2022 - mProfile.getAge()));
        locationNameTxt.setText(mProfile.getEducation());
        SwipeViewFragment.mProfile = mProfile;
    }

    @SwipeOut
    public void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    public void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }

    public String convertBitmapToArray(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public Bitmap getBitmapFromArray(String encoded) {
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}