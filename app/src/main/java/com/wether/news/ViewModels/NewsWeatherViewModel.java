package com.wether.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wether.news.ImageApi.Images;
import com.wether.news.ImageApi.JsonPlaceHolder;
import com.wether.news.Models.NewsweatherModel;
import com.wether.news.Repositories.NewsTopicRepo;
import com.wether.news.Repositories.WeatherPlaceRepo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsWeatherViewModel extends ViewModel {
    private MutableLiveData< List<NewsweatherModel>> mutableLiveData;
    private MutableLiveData<Boolean> isUpdating=new MutableLiveData<>();
    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    public void init(){
        if (mutableLiveData!=null)
            return;
        NewsTopicRepo newsRepo = NewsTopicRepo.getInstance();
        isUpdating.setValue(true);
        mutableLiveData= newsRepo.getNewsWeather();
        isUpdating= newsRepo.getIsUpdating();

    }
    public void initWeather(){
        if (mutableLiveData!=null)
            return;
        WeatherPlaceRepo weatherPlaceRepo = WeatherPlaceRepo.getInstance();
        mutableLiveData= weatherPlaceRepo.getNewsWeather();
        isUpdating= weatherPlaceRepo.getIsUpdating();
    }

    public LiveData< List<NewsweatherModel>> getNewsWeather(){
        return mutableLiveData;
    }

    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }


    public void getImages(String searchQuery,Map<String,Object> map,String type){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        Call<Images> imagesCall=jsonPlaceHolder.getImage("22376788-945cce53de128247cfdd90851",searchQuery,"photo");
        imagesCall.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                if (!response.isSuccessful()){
                    //Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v("tag","enteres");
                Images images=response.body();
                assert images != null;
                if (images.getTotal()==0){
                    //Toast.makeText(getContext(),"Write meaningful",Toast.LENGTH_LONG).show();
                    return;
                }
                String imageUrl=images.getHits().get(0).getPreviewURL();
                //addATopic(searchQuery,imageUrl);
                map.put("imageUrl",imageUrl);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child(type).push().setValue(map);

                Log.v("tag",imageUrl);
            }

            @Override
            public void onFailure(Call<Images> call, Throwable t) {
                //hideProgressDialog();
                //Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.v("tag",t.getMessage());
            }
        });
    }
}
