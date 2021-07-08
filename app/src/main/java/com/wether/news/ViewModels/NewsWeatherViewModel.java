package com.wether.news.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wether.news.Models.NewsweatherModel;
import com.wether.news.Repositories.NewsRepo;
import com.wether.news.Repositories.WeatherRepo;

import java.util.List;

public class NewsWeatherViewModel extends ViewModel {
    private MutableLiveData< List<NewsweatherModel>> mutableLiveData;
    private NewsRepo newsRepo;
    private WeatherRepo weatherRepo;
    public void init(){
        if (mutableLiveData!=null)
            return;
        newsRepo= NewsRepo.getInstance();
        mutableLiveData=newsRepo.getNewsWeather();
    }
    public void initWeather(){
        if (mutableLiveData!=null)
            return;
        weatherRepo= WeatherRepo.getInstance();
        mutableLiveData=weatherRepo.getNewsWeather();
    }

    public LiveData< List<NewsweatherModel>> getNewsWeather(){
        return mutableLiveData;
    };



}
