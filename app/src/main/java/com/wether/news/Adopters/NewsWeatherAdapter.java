package com.wether.news.Adopters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wether.news.Activity.NewsWeatherActivity;
import com.wether.news.Constants;
import com.wether.news.Models.NewsWeatherModel;
import com.wether.news.databinding.ItemLayoutBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NewsWeatherAdapter extends RecyclerView.Adapter<NewsWeatherAdapter.ViewHolder> {
    private List<NewsWeatherModel> newsWeatherModels;
    private final Context context;
    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    public NewsWeatherAdapter(List<NewsWeatherModel> newsWeatherModels, Context context) {
        this.newsWeatherModels = newsWeatherModels;
        this.context = context;
    }
    public void setNewsWeatherChanges(List<NewsWeatherModel> newsWeatherModels){
        this.newsWeatherModels=newsWeatherModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLayoutBinding itemLayoutBinding=ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsWeatherAdapter.ViewHolder holder, int position) {
        NewsWeatherModel model= newsWeatherModels.get(position);
        holder.itemLayoutBinding.cityNameNews.setText(model.getTitle());
        holder.itemLayoutBinding.lastSeen.setText(model.getLastSeen());
        Glide.with(context).load(model.getImageUrl()).into(holder.itemLayoutBinding.image);
        holder.itemLayoutBinding.card.setOnClickListener(view -> {
            Date d = new Date();
            FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(uId).child(model.getType())
                    .child(model.getKey()).child(Constants.TIME_STAMP).setValue(d.getTime());
            Intent intent=new Intent(context, NewsWeatherActivity.class);
            ArrayList<String> topics = new ArrayList<>();
            ArrayList<String> imagesUrls = new ArrayList<>();

            for (NewsWeatherModel model1: newsWeatherModels) {
                topics.add(model1.getTitle());
                imagesUrls.add(model1.getImageUrl());
            }
            intent.putExtra(Constants.TOPICS,topics);
            intent.putExtra(Constants.IMAGE_URLS,imagesUrls);
            intent.putExtra(Constants.POSITION,position);
            intent.putExtra(Constants.TYPE,model.getType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (newsWeatherModels==null)
            return 0;
        return newsWeatherModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLayoutBinding itemLayoutBinding;
        public ViewHolder(@NonNull ItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding=itemLayoutBinding;


        }
    }

}
