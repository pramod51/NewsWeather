package com.wether.news;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

public class Constants {
    public static final String URLS="urls";
    public static final String TYPE="type";
    public static final String POSITION="position";
    public static final String TOPICS_PLACES="topic_place";
    public static final String TOPICS="topic";
    public static final String PLACES="place";
    public static final String TIME_STAMP="timeStamp";
    public static final String USERS="Users";
    public static final String IMAGE_URLS="images_url";
    public static final String IMAGE_URL="imageUrl";
    public static final String NEWS="News";
    public static final String WEATHER="Weather";
    public static final String EMAIL="email";
    public static final String PASSWORD="pass";



    public static String getTimeAgo(long time) {
        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        //long time = date.getTime();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return "in the future";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "moments ago";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a min ago";
        } else if (diff < 60 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " min ago";
        } else if (diff < 2 * HOUR_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTodayDate(){
        Date d = new Date();
        return DateFormat.format("yyyy-MM-dd", d.getTime()).toString();
    }
}
