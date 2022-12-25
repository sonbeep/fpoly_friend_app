package com.ltmt5.fpoly_friend_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.databinding.ItemHobbiesBinding;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.List;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ViewHolder> {
    private final Context context;
    private final ItemClick itemClick;
    boolean isMultiple;
    private List<Hobbies> list;

    public HobbiesAdapter(Context context, ItemClick itemClick) {
        this.context = context;
        this.itemClick = itemClick;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHobbiesBinding binding = ItemHobbiesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position));

    }

    public void setData(List<Hobbies> mList, String data) {
        this.list = mList;
        for (Hobbies hobbies : list) {
            if (hobbies.getName().contains(data)) {
                hobbies.setSelected(true);
            }
        }
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
        void clickItem(Hobbies hobbies);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemHobbiesBinding binding;

        public ViewHolder(ItemHobbiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void bindData(Hobbies hobbies) {
            if (hobbies.isSelected()) {
                binding.tv1.setTextColor(ContextCompat.getColor(context, R.color.prime_1));
                binding.btnHobbies.setStrokeColor(ContextCompat.getColor(context, R.color.prime_1));
            } else {
                binding.tv1.setTextColor(ContextCompat.getColor(context, R.color.prime_4));
                binding.btnHobbies.setStrokeColor(ContextCompat.getColor(context, R.color.prime_4));
            }
            binding.tv1.setText(hobbies.getName());
            binding.btnHobbies.setOnClickListener(v -> {
                if (!isMultiple) {
                    hobbies.setSelected(true);
                    handleClick(hobbies);
                } else {
                    hobbies.setSelected(!hobbies.isSelected());
                    notifyDataSetChanged();
                }
                itemClick.clickItem(hobbies);
            });

        }

        private void handleClick(Hobbies hoobies) {
            for (int i = 0; i < list.size(); i++) {
                if (!hoobies.equals(list.get(i))) {
                    list.get(i).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }
    }
}
