package com.wether.news.ImageApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolder {

    @GET("api/")
    Call<Images> getImage(@Query("key") String key,
                          @Query("q") String searchQuery,
                          @Query("image_type") String imageType);

}
