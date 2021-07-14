package com.wether.news.Adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wether.news.Models.NewsModel;
import com.wether.news.databinding.NewsItemBinding;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<NewsModel> newsModels;
    Context context;

    public NewsAdapter(List<NewsModel> newsModels, Context context) {
        this.newsModels = newsModels;
        this.context = context;
    }
    public void setNewsModels(List<NewsModel> newsModels){
        this.newsModels=newsModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemBinding  newsItemBinding=NewsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(newsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsModel model=newsModels.get(position);
        holder.newsItemBinding.title.setText(model.getTitle());
        holder.newsItemBinding.description.setText(model.getDescription());
        holder.newsItemBinding.publishedAt.setText(model.getPublishedAt());
        holder.newsItemBinding.newsSource.setText(model.getNewsSource());
        Glide.with(context).load(model.getImageUrl()).into(holder.newsItemBinding.image);

    }

    @Override
    public int getItemCount() {
    if (newsModels!=null)
        return this.newsModels.size();
    else
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NewsItemBinding newsItemBinding;
        public ViewHolder(@NonNull NewsItemBinding newsItemBinding) {
            super(newsItemBinding.getRoot());
            this.newsItemBinding=newsItemBinding;
        }
    }
}
