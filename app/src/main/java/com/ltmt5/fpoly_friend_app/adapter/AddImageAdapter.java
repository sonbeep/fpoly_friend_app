package com.ltmt5.fpoly_friend_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ItemAddImageBinding;

import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder> {
    private List<Bitmap> list;
    private final Context context;
    private final ItemClick itemClick;

    public AddImageAdapter(List<Bitmap> list, Context context, ItemClick itemClick) {
        this.list = list;
        this.context = context;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddImageBinding binding = ItemAddImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public interface ItemClick {
        void clickItem(int position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position), position);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Bitmap> list) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAddImageBinding binding;

        public ViewHolder(ItemAddImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Bitmap bitmap, int position) {
            if (bitmap != null) {
                Glide.with(context).load(bitmap).centerCrop().into(binding.imgAddImage);
                binding.btnDelete.setVisibility(View.VISIBLE);
            } else {
                binding.imgAddImage.setImageResource(R.drawable.ic_add);
                binding.btnDelete.setVisibility(View.GONE);
            }
            binding.btnDelete.setOnClickListener(v -> {
                itemClick.clickItem(position);
            });
        }
    }
}
