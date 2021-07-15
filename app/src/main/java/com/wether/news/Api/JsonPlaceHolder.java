package com.wether.news.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolder {

    @GET("api/")
    Call<Images> getImage(@Query("key") String key,
                          @Query("q") String searchQuery,
                          @Query("image_type") String imageType);
    @GET("v2/everything")
    Call<News> getNews(@Query("q") String searchQuery,
                       @Query("from") String fromToday,
                       @Query("sortBy") String sortBy,
                       @Query("apiKey") String apiKey);

    @GET("v1/current.json")
    Call<Weather> getWeather(@Query("key") String key,
                             @Query("q") String searchQuery,
                             @Query("aqi") String aqi);

}
