package com.wether.news.Models;

public class NewsModel {
    String title,description,publishedAt,newsSource,imageUrl;

    public NewsModel(String title, String description, String publishedAt, String newsSource, String imageUrl) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.newsSource = newsSource;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public String getPublishedAt() {
        return publishedAt;
    }


    public String getNewsSource() {
        return newsSource;
    }


    public String getImageUrl() {
        return imageUrl;
    }

}
