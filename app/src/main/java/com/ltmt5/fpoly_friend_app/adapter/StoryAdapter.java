package com.ltmt5.fpoly_friend_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.R;

import java.util.List;

public class StoryAdapter extends PagerAdapter {
    private Context context;
    private List<Bitmap> list;

    public StoryAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_story, container, false);
        ImageView imageView = view.findViewById(R.id.imgThump);
        if (list.get(position) != null) {
            Glide.with(context).load(list.get(position)).centerCrop().into(imageView);
        }
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
