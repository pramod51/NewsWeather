package com.wether.news.Repositories;

import android.util.Log;


import com.wether.news.WetherApi.Weather;
import com.wether.news.WetherApi.WeatherJsonPlaceHolder;
import com.wether.news.WetherApi.WeatherRetroInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    public interface GetWeatherData{
        void onResponse(Weather weather);
        void onFailure(String error);
        void isLoading(Boolean isLoading);
    }


    public void getWeatherData(String searchQuery,GetWeatherData getWeatherData){
        Log.v(WeatherRepository.class.getSimpleName(),"getting data");
        getWeatherData.isLoading(true);

        WeatherJsonPlaceHolder jsonPlaceHolder = WeatherRetroInstance.getRetrofitClient().create(WeatherJsonPlaceHolder.class);

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
                Log.e(WeatherRepository.class.getSimpleName(),t.getMessage());
            }
        });
    }


}
