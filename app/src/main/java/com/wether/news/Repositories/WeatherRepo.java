package com.wether.news.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wether.news.Models.NewsweatherModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherRepo {


    protected interface FirebaseCallback{
        String response(long timestamp);
    }
    protected interface FirebaseDataCallback{
        Void responseData(List<NewsweatherModel> models);
    }


    private String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private static WeatherRepo instance;
    List<NewsweatherModel> newsWeather=new ArrayList<>();
    MutableLiveData<List<NewsweatherModel>> newsWeatherData;
    MutableLiveData<Boolean> isUpdating=new MutableLiveData<>();


    public static WeatherRepo getInstance(){
        if (instance==null){
            instance=new WeatherRepo();
        }
        return instance;
    }

    public MutableLiveData<List<NewsweatherModel>> getNewsWeather(){
        setWeather();
        newsWeatherData=new MutableLiveData<>();
        newsWeatherData.setValue(newsWeather);

        return newsWeatherData;
    }
    public MutableLiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }
    FirebaseDataCallback dataCallback=new FirebaseDataCallback() {
        @Override
        public Void responseData(List<NewsweatherModel> models) {
            newsWeatherData.setValue(models);
            Log.v("tag","size"+models.size());
            return null;
        }
    };
    private void setWeather(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child("Weather")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newsWeather.clear();
                        if (snapshot.exists())
                            for (DataSnapshot ds:snapshot.getChildren()){
                                Log.v("tag","ago=="+callback.response((Long) ds.child("timeStamp").getValue()));
                                newsWeather.add(new NewsweatherModel(ds.child("topic").getValue(String.class),callback.response((Long) ds.child("timeStamp").getValue()),
                                        ds.child("imageUrl").getValue(String.class),"Weather", ds.getKey()));
                            }
                        dataCallback.responseData(newsWeather);
                        isUpdating.setValue(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    FirebaseCallback callback=new FirebaseCallback() {
        @Override
        public String response(long timestamp) {
            return getTimeAgo(timestamp);
        }
    };
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

    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }


}
