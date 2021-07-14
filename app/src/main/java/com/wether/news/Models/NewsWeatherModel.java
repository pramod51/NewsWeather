package com.wether.news.Models;

public class NewsWeatherModel {
    private String title;
    private final String lastSeen;
    private final String imageUrl;
    private String type;
    private final String key;

    public NewsWeatherModel(String title, String lastSeen, String imageUrl, String type, String key) {
        this.title = title;
        this.lastSeen = lastSeen;
        this.imageUrl = imageUrl;
        this.type = type;
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastSeen() {
        return lastSeen;
    }

}
