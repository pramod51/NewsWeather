package com.wether.news.Adopters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wether.news.Activity.NewsWeatherActivity;
import com.wether.news.Models.NewsweatherModel;
import com.wether.news.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NewsWeatherAdapter extends RecyclerView.Adapter<NewsWeatherAdapter.ViewHolder> {
    private final List<NewsweatherModel> newsweatherModels;
    private final Context context;
    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    public NewsWeatherAdapter(List<NewsweatherModel> newsweatherModels, Context context) {
        this.newsweatherModels = newsweatherModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsWeatherAdapter.ViewHolder holder, int position) {
        NewsweatherModel model=newsweatherModels.get(position);
        holder.title.setText(model.getTitle());
        holder.lastSeen.setText(model.getLastSeen());
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
        holder.cardView.setOnClickListener(view -> {
            Date d = new Date();
            FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child(model.getType())
                    .child(model.getKey()).child("timeStamp").setValue(d.getTime());
            Intent intent=new Intent(context, NewsWeatherActivity.class);
            ArrayList<String> topics = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();

            for (NewsweatherModel model1:newsweatherModels) {
                topics.add(model1.getTitle());
                images.add(model1.getImageUrl());
            }
            intent.putExtra("key",topics);
            intent.putExtra("urls",images);
            intent.putExtra("pos",position);
            intent.putExtra("type",model.getType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsweatherModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,lastSeen;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.city_name_news);
            lastSeen=itemView.findViewById(R.id.last_seen);
            cardView=itemView.findViewById(R.id.card);


        }
    }

}
