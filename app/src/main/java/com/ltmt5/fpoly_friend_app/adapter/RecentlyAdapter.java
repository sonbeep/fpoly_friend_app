package com.ltmt5.fpoly_friend_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ItemRecentlyBinding;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.List;

public class RecentlyAdapter extends RecyclerView.Adapter<RecentlyAdapter.ViewHolder> {
    private final Context context;
    private final ItemClick itemClick;
    private List<UserProfile> list;

    public RecentlyAdapter(Context context, ItemClick itemClick) {
        this.context = context;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecentlyBinding binding = ItemRecentlyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position));

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<UserProfile> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public interface ItemClick {
        void clickItem(UserProfile userProfile);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecentlyBinding binding;

        public ViewHolder(ItemRecentlyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(UserProfile userProfile) {
//            if (userProfile.getAvailability() == 0) {
//                binding.btnAvailable.setVisibility(View.VISIBLE);
//            } else {
                binding.btnAvailable.setVisibility(View.INVISIBLE);
//            }
            Glide.with(context).load(userProfile.getImageUri()).centerCrop().error(R.drawable.demo1).into(binding.imgAvatar);
            binding.imgAvatar.setOnClickListener(view -> {
                itemClick.clickItem(userProfile);
            });
        }
    }
}
