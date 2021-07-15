package com.wether.news.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstances {

    public static String IMAGE_BASE_URL = "https://pixabay.com/";

    public static String WEATHER_BASE_URL = "https://api.weatherapi.com/";

    public static String NEWS_BASE_URL = "https://newsapi.org/";

    private static Retrofit imageRetrofit;

    private static Retrofit weatherRetrofit;

    private static Retrofit newsRetrofit;

    public static Retrofit getImageRetrofitClient() {
        if(imageRetrofit == null ) {
            imageRetrofit = new Retrofit.Builder()
                    .baseUrl(IMAGE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return imageRetrofit;
    }


    public static Retrofit getNewsRetrofitClient() {
        if(newsRetrofit == null ) {
            newsRetrofit = new Retrofit.Builder()
                    .baseUrl(NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return newsRetrofit;
    }

    public static Retrofit getWeatherRetrofitClient() {
        if(weatherRetrofit == null ) {
            weatherRetrofit = new Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherRetrofit;
    }
}
