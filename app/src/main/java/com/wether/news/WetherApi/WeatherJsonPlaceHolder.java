package com.wether.news.WetherApi;

import com.wether.news.NewsApi.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherJsonPlaceHolder {

    @GET("v1/current.json")
    Call<Weather> getWeather(@Query("key") String key,
                       @Query("q") String searchQuery,
                       @Query("aqi") String aqi);

}
