package com.wether.news.Repositories;

import android.util.Log;


import com.wether.news.WetherApi.Weather;
import com.wether.news.WetherApi.WeatherJsonPlaceHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {
    public interface GetWeatherData{
        void onResponse(Weather weather);
        void onFailure(String error);
        void isLoading(Boolean isLoading);
    }


    public void getWeatherData(String searchQuery,GetWeatherData getWeatherData){
        Log.v("tag","getting data");
        getWeatherData.isLoading(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherJsonPlaceHolder jsonPlaceHolder = retrofit.create(WeatherJsonPlaceHolder.class);

        Call<Weather> weatherCall=jsonPlaceHolder.getWeather("31190e610bb04c82b42123859210707",searchQuery,"yes");
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                getWeatherData.isLoading(false);
                if (!response.isSuccessful()){
                    getWeatherData.onResponse(null);
                    getWeatherData.onFailure("Something went wrong");
                    return;
                }
                Log.v("tag","entered");
                Weather weather=response.body();
                if (weather==null) {
                    getWeatherData.onResponse(null);
                    getWeatherData.onFailure("No Result Found");
                    return;
                }
                getWeatherData.onResponse(weather);

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                getWeatherData.onFailure(t.getMessage());
                getWeatherData.isLoading(false);
                Log.v("tag",t.getMessage());
            }
        });
    }


}
