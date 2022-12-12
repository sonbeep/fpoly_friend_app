package com.ltmt5.fpoly_friend_app.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ItemMessageBinding;
import com.ltmt5.fpoly_friend_app.listener.ConversionListener;
import com.ltmt5.fpoly_friend_app.model.ChatMessage;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.List;

public class RecentConversionAdapter extends RecyclerView.Adapter<RecentConversionAdapter.ConversionViewHolder> {
    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversionAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemMessageBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    private Bitmap getConversionImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding binding;

        ConversionViewHolder(ItemMessageBinding itemContainerRecentConversionBinding) {
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
        }

        void setData(ChatMessage chatMessage) {
//            binding.cvAvatar.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            Glide.with(App.context).load(chatMessage.conversionImage).centerCrop().into(binding.cvAvatar);
            binding.tvName.setText(chatMessage.conversionName);
            binding.tvDescription.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                UserProfile user = new UserProfile();
                user.setUserId(chatMessage.conversionId);
                user.setName(chatMessage.conversionName);
                user.setImageUri(chatMessage.conversionImage);
                conversionListener.onConversionClicked(user);
            });
        }
    }
}
