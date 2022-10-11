package com.ltmt5.fpoly_friend_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.model.Chat;

import java.util.List;

public class SwipeAdapter extends BaseAdapter {
    private Context context;
    private List<Chat> list;

    public SwipeAdapter(Context context, List<Chat> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe, parent, false);
        } else {
            view = convertView;
            Chat chat = (Chat) getItem(position);
            ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
            TextView tvInfo = convertView.findViewById(R.id.tvInfo);

            int drawableId = context.getResources().getIdentifier(chat.getAvatar(), "drawable", context.getPackageName());
            imgAvatar.setImageResource(drawableId);

            tvInfo.setText(chat.getName()+", "+chat.getCount());

        }
        return view;
    }
}
