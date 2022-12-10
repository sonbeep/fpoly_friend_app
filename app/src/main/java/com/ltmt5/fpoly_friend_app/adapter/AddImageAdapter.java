package com.ltmt5.fpoly_friend_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
    private final Context context;
    private final ItemClick itemClick;
    private List<Uri> list;

    public AddImageAdapter( Context context, ItemClick itemClick) {
        this.context = context;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddImageBinding binding = ItemAddImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position), position);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Uri> list) {
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
        void clickItem(int position);

        void deleteItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAddImageBinding binding;

        public ViewHolder(ItemAddImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Uri uri, int position) {
            if (uri != null) {
                Glide.with(context).load(uri).centerCrop().into(binding.imgAddImage);
//                binding.btnDelete.setVisibility(View.VISIBLE);
            } else {
                binding.imgAddImage.setImageResource(R.drawable.ic_add);
//                binding.btnDelete.setVisibility(View.GONE);
            }
            binding.btnAddImage.setOnClickListener(view -> {
                if (uri == null) {
                    itemClick.clickItem(position);
                }
            });

            binding.btnDelete.setOnClickListener(v -> {
                itemClick.deleteItem(position);
            });
        }
    }
}
