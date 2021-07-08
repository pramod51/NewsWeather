package com.wether.news.Models;

public class NewsweatherModel {
    private String title,lastSeen,imageUrl,type,key;

    public NewsweatherModel(String title, String lastSeen, String imageUrl, String type, String key) {
        this.title = title;
        this.lastSeen = lastSeen;
        this.imageUrl = imageUrl;
        this.type = type;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }
}
