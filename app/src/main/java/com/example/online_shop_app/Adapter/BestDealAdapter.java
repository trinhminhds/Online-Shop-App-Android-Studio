package com.example.online_shop_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.Activity.DetailActivity;
import com.example.online_shop_app.Domain.ItemDomain;
import com.example.online_shop_app.databinding.ViewholderBestDealBinding;

import java.util.ArrayList;
import java.util.Objects;

public class BestDealAdapter extends RecyclerView.Adapter<BestDealAdapter.Viewholder> {
    private ArrayList<ItemDomain> items;
    private Context context;

    public BestDealAdapter(ArrayList<ItemDomain> item) {
        this.items = item;
    }

    @NonNull
    @Override
    public BestDealAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        ViewholderBestDealBinding binding = ViewholderBestDealBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BestDealAdapter.Viewholder holder, int position) {
        holder.binding.titleTxt.setText(items.get(position).getTitle());
        holder.binding.priceTxt.setText(items.get(position).getPrice() + " $/kg");

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .into(holder.binding.img);


        holder.itemView.setOnClickListener(v -> {
            Intent intent  = new Intent(context, DetailActivity.class);
            intent.putExtra("object",items.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderBestDealBinding binding;
        public Viewholder(ViewholderBestDealBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public void setData(ArrayList<ItemDomain> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

}
