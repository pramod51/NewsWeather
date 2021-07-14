package com.wether.news.ViewModels;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wether.news.Models.NewsWeatherModel;
import com.wether.news.Repositories.ImageRepository;
import com.wether.news.Repositories.NewsTopicRepo;
import com.wether.news.Repositories.WeatherPlaceRepo;

import java.util.List;
import java.util.Map;

public class NewsWeatherViewModel extends ViewModel {
    private MutableLiveData< List<NewsWeatherModel>> newsWeatherMutableLD;
    private final MutableLiveData<Boolean> isUpdating=new MutableLiveData<>();
    private ImageRepository imageRepository;
    public void initNewsTopics(){
        if (newsWeatherMutableLD!=null)
            return;
        newsWeatherMutableLD=new MutableLiveData<>();
        NewsTopicRepo newsTopicRepo = NewsTopicRepo.getInstance();
        isUpdating.setValue(true);
        newsTopicRepo.setNewsTopics(firebaseNewsTopics);

    }

    public void initWeatherPLace(){
        if (newsWeatherMutableLD!=null)
            return;
        newsWeatherMutableLD=new MutableLiveData<>();
        WeatherPlaceRepo weatherPlaceRepo = WeatherPlaceRepo.getInstance();
        weatherPlaceRepo.setWeatherPlace(firebaseWeatherPlace);
    }

    public LiveData< List<NewsWeatherModel>> getNewsWeather(){
        return newsWeatherMutableLD;
    }

    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }

    private final NewsTopicRepo.FirebaseNewsTopics firebaseNewsTopics=new NewsTopicRepo.FirebaseNewsTopics() {
        @Override
        public void onFailure(String error) {
            isUpdating.postValue(false);
            Log.e(NewsWeatherViewModel.class.getSimpleName(),error+"");
        }

        @Override
        public void responseData(List<NewsWeatherModel> newsWeatherModels) {
            Log.v(NewsWeatherViewModel.class.getSimpleName(),"Data Received in ViewModel");
            newsWeatherMutableLD.postValue(newsWeatherModels);
            isUpdating.postValue(true);
        }
    };

    private final WeatherPlaceRepo.FirebaseWeatherPlace firebaseWeatherPlace=new WeatherPlaceRepo.FirebaseWeatherPlace() {
        @Override
        public void onFailure(String error) {
            Log.e(NewsWeatherViewModel.class.getSimpleName(),error+"");
            isUpdating.postValue(false);
        }

        @Override
        public void responseData(List<NewsWeatherModel> newsWeatherModels) {
            Log.v(NewsWeatherViewModel.class.getSimpleName(),"Data Received in ViewModel");
            newsWeatherMutableLD.postValue(newsWeatherModels);
            isUpdating.postValue(false);
        }
    };

    public void getImages(String searchQuery,Map<String,Object> map,String type){

        if (imageRepository==null)
            imageRepository=new ImageRepository();
        imageRepository.getImageUrl(searchQuery, map, type);
    }
}
