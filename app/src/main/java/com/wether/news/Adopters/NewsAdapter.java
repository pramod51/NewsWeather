package com.wether.news.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wether.news.Models.NewsModel;
import com.wether.news.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<NewsModel> newsModels;
    Context context;

    public NewsAdapter(ArrayList<NewsModel> newsModels, Context context) {
        this.newsModels = newsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsModel model=newsModels.get(position);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.publishedAt.setText(model.getPublishedAt());
        holder.source.setText(model.getNewsSource());
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,description,publishedAt,source;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            publishedAt=itemView.findViewById(R.id.published_at);
            source=itemView.findViewById(R.id.news_source);

        }
    }
}
