package com.example.online_shop_app.Activity;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.Adapter.SimilarAdapter;
import com.example.online_shop_app.Domain.ItemDomain;
import com.example.online_shop_app.databinding.ActivityDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicMarkableReference;


public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private ItemDomain object;
    private int weight = 1;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
        setVariable();
        initSmilerView();
    }

    private void initSmilerView() {
        DatabaseReference myRef = database.getReference("Items");
        binding.progressBarSimilar.setVisibility(View.VISIBLE);
        ArrayList<ItemDomain> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(ItemDomain.class));
                    }
                }
                if(!list.isEmpty()) {
                    binding.recyclerViewSimilar.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    binding.recyclerViewSimilar.setAdapter(new SimilarAdapter(list));
                }
                binding.progressBarSimilar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setVariable(){
        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.img);

        binding.priceKgText.setText(object.getPrice()+"$/Kg");
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.ratingBar.setRating((float) object.getStart());
        binding.ratingTxt.setText(object.getStart()+"");
        binding.totalTxt.setText(weight*object.getPrice()+"$");

        binding.plusBtn.setOnClickListener(v -> {
            weight += 1;
            binding.weightTxt.setText(weight+" kg");
            binding.totalTxt.setText(weight*object.getPrice()+"$");
        });

        binding.minusBtn.setOnClickListener(v -> {
            if (weight > 1){
                weight -= 1;
                binding.weightTxt.setText(weight+" kg");
                binding.totalTxt.setText(weight*object.getPrice()+"$");
            }
        });

    }

    private void getBundles(){
        object = (ItemDomain) getIntent().getSerializableExtra("object");
    }



}