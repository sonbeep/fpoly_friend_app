package com.ltmt5.fpoly_friend_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltmt5.fpoly_friend_app.databinding.ItemLoveBinding;
import com.ltmt5.fpoly_friend_app.model.Chat;

import java.util.List;

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.ViewHolder> {
    private List<Chat> list;
    private final Context context;
    private final ItemClick itemClick;

    public LoveAdapter(List<Chat> list, Context context, ItemClick itemClick) {
        this.list = list;
        this.context = context;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoveBinding binding = ItemLoveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public interface ItemClick {
        void clickItem(Chat chat);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position));

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Chat> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLoveBinding binding;

        public ViewHolder(ItemLoveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Chat chat) {
            int drawableId = context.getResources().getIdentifier(chat.getAvatar(), "drawable", context.getPackageName());
            binding.imgAvatar.setImageResource(drawableId);
            binding.tvInfo.setText(chat.getName() + ", " + chat.getCount());
        }
    }
}
