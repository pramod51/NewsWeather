package com.wether.news.Repositories;

import android.util.Log;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wether.news.Api.JsonPlaceHolder;
import com.wether.news.Api.RetroInstances;
import com.wether.news.Api.Weather;
import com.wether.news.Constants;
import com.wether.news.Models.NewsWeatherModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class WeatherRepository {

    public interface FirebaseWeatherPlace{
        void onFailure(String error);
        void responseData(List<NewsWeatherModel> newsWeatherModels);
    }


    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private static WeatherRepository instance;
    List<NewsWeatherModel> newsWeather=new ArrayList<>();


    public static WeatherRepository getInstance(){
        if (instance==null){
            instance=new WeatherRepository();
        }
        return instance;
    }

    public void setWeatherPlace(WeatherRepository.FirebaseWeatherPlace firebaseWeatherPlace){
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










    public interface GetWeatherData{
        void onResponse(Weather weather);
        void onFailure(String error);
        void isLoading(Boolean isLoading);
    }


    public void getWeatherData(String searchQuery,GetWeatherData getWeatherData){
        Log.v(WeatherRepository.class.getSimpleName(),"getting data");
        getWeatherData.isLoading(true);

        JsonPlaceHolder jsonPlaceHolder = RetroInstances.getWeatherRetrofitClient().create(JsonPlaceHolder.class);

        Call<Weather> weatherCall=jsonPlaceHolder.getWeather("31190e610bb04c82b42123859210707",searchQuery,"yes");
        weatherCall.enqueue(new Callback<Weather>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                getWeatherData.isLoading(false);
                if (!response.isSuccessful()){
                    getWeatherData.onResponse(null);
                    getWeatherData.onFailure("Something went wrong");
                    return;
                }
                Weather weather=response.body();
                if (weather==null) {
                    getWeatherData.onResponse(null);
                    getWeatherData.onFailure("No Result Found");
                    return;
                }
                getWeatherData.onResponse(weather);

            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                getWeatherData.onFailure(t.getMessage());
                getWeatherData.isLoading(false);
                Log.e(WeatherRepository.class.getSimpleName(),t.getMessage());
            }
        });
    }


}
