package com.ltmt5.fpoly_friend_app.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.ybq.parallaxviewpager.ParallaxViewPager;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityProfileBinding;
import com.ltmt5.fpoly_friend_app.help.ImageLoader;
import com.ltmt5.fpoly_friend_app.model.Hobbies;
import com.ltmt5.fpoly_friend_app.model.Profile;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.fragment.SwipeViewFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements HobbiesAdapter.ItemClick {
    ActivityProfileBinding binding;
    UserProfile userProfileInfo;
    List<Bitmap> bitmaps = new ArrayList<>();
    Context context;
    private boolean swipeViewSource;
    private ImageLoader imageLoader;
    private CardView profileImageCard;
    HobbiesAdapter hobbiesAdapter;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        context = App.context;
//        for (Bitmap bitmap : Question6Activity.bitmapList) {
//            if (bitmap != null) {
//                bitmaps.add(bitmap);
//            }
//        }
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(getList(), this, this);
        binding.recHobbies.setLayoutManager(layoutManager);
        binding.recHobbies.setAdapter(hobbiesAdapter);

//                imageLoader = App.getImageLoader();

        userProfileInfo = (UserProfile) getIntent().getSerializableExtra(SwipeViewFragment.EXTRA_USER_PROFILE);
        swipeViewSource = getIntent().getExtras().getBoolean(SwipeViewFragment.EXTRA_SWIPE_VIEW_SOURCE);

//        userProfileInfo = SwipeViewFragment.mProfile;
//        swipeViewSource = true;


        binding.tvName.setText(userProfileInfo.getName());
        binding.tvAge.setText( userProfileInfo.getAge()+"");
        binding.tvDistance.setText("cách xa 5 km");

        profileImageCard = findViewById(R.id.user_swipe_card_view);
        binding.tvDescription.setText(userProfileInfo.getName());

        hobbiesAdapter.setData(getList());

//        if (!swipeViewSource) {
//            TextView matchValue = findViewById(R.id.profile_match_text_view);
//            matchValue.setText("match in " + 87 + "%!");
//            matchValue.setVisibility(View.VISIBLE);
//            binding.profileMatchHeart.setVisibility(View.VISIBLE);
//        }

        if (!swipeViewSource) binding.profileFab.setVisibility(View.VISIBLE);

        binding.profileFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(profileImageCard, "radius", 0);
                animator.setDuration(250);
                animator.start();
            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                if (swipeViewSource) showFab();
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        initViewPager(userProfileInfo);
    }

    List<Hobbies> getList() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Thế hệ 9x"));
        list.add(new Hobbies("Harry Potter"));
        list.add(new Hobbies("SoundCloud"));
        list.add(new Hobbies("Spa"));
        list.add(new Hobbies("Chăm sóc bản thân"));
        list.add(new Hobbies("Heavy Metal"));
        return list;
    }

    private void initViewPager(final UserProfile userProfileInfo) {
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(container.getContext(), R.layout.parallax_viewpager_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
//                Glide.with(context).load(bitmaps.get(position)).centerCrop().into(imageView);
//                Glide.with(context).load(Uri.parse(userProfileInfo.getImageUri())).centerCrop().error(R.drawable.demo1).into(imageView);
//                imageLoader.downloadImage(userProfileInfo.parallax_viewpager_item.xml().get(position), imageView);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public int getCount() {
//                return bitmaps.size();
                return 1;
            }
        };
        binding.parallaxViewpager.setAdapter(adapter);
    }


    @SuppressLint("RestrictedApi")
    private void showFab() {
        binding.profileFab.animate().cancel();
        binding.profileFab.setScaleX(0f);
        binding.profileFab.setScaleY(0f);
        binding.profileFab.setAlpha(0f);
        binding.profileFab.setVisibility(View.VISIBLE);
        binding.profileFab.animate().setDuration(200).scaleX(1).scaleY(1).alpha(1).setInterpolator(new LinearOutSlowInInterpolator());
    }

    @SuppressLint("RestrictedApi")
    private void hideFab() {
        binding.profileFab.animate().cancel();
        binding.profileFab.animate().setDuration(200).scaleX(0).scaleY(0).alpha(0).setInterpolator(new LinearOutSlowInInterpolator());
        binding.profileFab.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
        if (swipeViewSource) hideFab();
    }

    @Override
    public void clickItem(Hobbies hobbies) {

    }
}
