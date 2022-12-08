package com.ltmt5.fpoly_friend_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.databinding.ItemLoveBinding;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.List;

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.ViewHolder> {
    private List<UserProfile> list;
    private final Context context;
    private final ItemClick itemClick;

    public LoveAdapter(Context context, ItemClick itemClick) {
        this.context = context;
        this.itemClick = itemClick;
    }

    public void setData(List<UserProfile> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoveBinding binding = ItemLoveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public interface ItemClick {
        void clickItem(UserProfile userProfile);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position));

    }


    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLoveBinding binding;

        public ViewHolder(ItemLoveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(UserProfile userProfile) {
            Glide.with(context).load(userProfile.getImageUri()).centerCrop().into(binding.imgAvatar);
            binding.tvInfo.setText(userProfile.getName() + ", " + userProfile.getAge());
            binding.getRoot().setOnClickListener(view -> itemClick.clickItem(userProfile));
        }
    }
}
