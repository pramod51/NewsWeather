package com.wether.news.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wether.news.Constants;
import com.wether.news.Models.NewsWeatherModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WeatherPlaceRepo {

    public interface FirebaseWeatherPlace{
        void onFailure(String error);
        void responseData(List<NewsWeatherModel> newsWeatherModels);
    }


    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private static WeatherPlaceRepo instance;
    List<NewsWeatherModel> newsWeather=new ArrayList<>();


    public static WeatherPlaceRepo getInstance(){
        if (instance==null){
            instance=new WeatherPlaceRepo();
        }
        return instance;
    }

    public void setWeatherPlace(FirebaseWeatherPlace firebaseWeatherPlace){
        FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(uId).child(Constants.WEATHER)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newsWeather.clear();
                        if (snapshot.exists())
                            for (DataSnapshot ds:snapshot.getChildren()){
                                newsWeather.add(new NewsWeatherModel(ds.child(Constants.TOPICS).getValue(String.class),
                                        Constants.getTimeAgo(Long.parseLong("" + ds.child(Constants.TIME_STAMP).getValue())),
                                        ds.child(Constants.IMAGE_URL).getValue(String.class),Constants.WEATHER, ds.getKey()));
                            }
                            firebaseWeatherPlace.responseData(newsWeather);
                            firebaseWeatherPlace.onFailure(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseWeatherPlace.onFailure(error.getMessage());
                    }
                });

    }




}
