package com.wether.news.WetherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetroInstance {

    public static String BASE_URL = "https://api.weatherapi.com/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if(retrofit == null ) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
