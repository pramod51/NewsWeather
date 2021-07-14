package com.wether.news.ImageApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageRetroInstance {

    public static String BASE_URL = "https://pixabay.com/";

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
