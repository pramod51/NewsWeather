package com.wether.news.NewsApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsJsonPlaceHolder {

    @GET("v2/everything")
    Call<News> getNews(@Query("q") String searchQuery,
                          @Query("from") String fromToday,
                          @Query("sortBy") String sortBy,
                          @Query("apiKey") String apiKey);

}
