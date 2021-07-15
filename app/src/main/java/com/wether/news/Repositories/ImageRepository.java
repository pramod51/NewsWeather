package com.wether.news.Repositories;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wether.news.Constants;
import com.wether.news.Api.RetroInstances;
import com.wether.news.Api.Images;
import com.wether.news.Api.JsonPlaceHolder;
import com.wether.news.ViewModels.NewsWeatherViewModel;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ImageRepository {

    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    public void getImageUrl(String searchQuery, Map<String,Object> map, String type){
        JsonPlaceHolder jsonPlaceHolder = RetroInstances.getImageRetrofitClient().create(JsonPlaceHolder.class);

        Call<Images> imagesCall=jsonPlaceHolder.getImage("22376788-945cce53de128247cfdd90851",searchQuery,"photo");
        imagesCall.enqueue(new Callback<Images>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                if (!response.isSuccessful()){

                    return;
                }
                Log.v(NewsWeatherViewModel.class.getSimpleName(),"Response Successful");
                Images images=response.body();
                assert images != null;
                if (images.getTotal()==0){
                    return;
                }
                String imageUrl=images.getHits().get(0).getPreviewURL();
                map.put(Constants.IMAGE_URL,imageUrl);
                FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(uId).child(type).push().setValue(map);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Images> call, Throwable t) {
                Log.e(ImageRepository.class.getSimpleName(),t.getMessage());
            }
        });
    }
}
