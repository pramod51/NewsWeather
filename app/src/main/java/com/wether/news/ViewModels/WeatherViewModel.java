package com.wether.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wether.news.Repositories.WeatherRepository;
import com.wether.news.WetherApi.Weather;


public class WeatherViewModel extends ViewModel {

    private MutableLiveData<Weather> weatherMutableLD;
    private MutableLiveData<String> failedMutableLD;
    private MutableLiveData<Boolean> isLoadingMutableLData;
    private MutableLiveData<Integer> positionMutableLd;
    private WeatherRepository weatherRepository;

    public LiveData<Weather> getWeather(String search){
        if (weatherMutableLD==null) {
            init();
            isLoadingMutableLData.postValue(true);
            weatherRepository.getWeatherData(search,getWeatherData);
        }

        return weatherMutableLD;
    }
    public void nextPrevWeatherUpdate(String search){
        init();
        isLoadingMutableLData.postValue(true);
        weatherRepository.getWeatherData(search,getWeatherData);
    }
    private void init(){
        if (weatherMutableLD==null)
            weatherMutableLD=new MutableLiveData<>();
        if (weatherRepository==null)
            weatherRepository=new WeatherRepository();
        if (isLoadingMutableLData==null)
            isLoadingMutableLData=new MutableLiveData<>();
        if (failedMutableLD==null)
            failedMutableLD=new MutableLiveData<>();
    }

    public LiveData<String> onFailureData(){
        return failedMutableLD;
    }
    public LiveData<Boolean> isLoading(){
        return isLoadingMutableLData;
    }
    public LiveData<Integer> getPosition(){
        if (positionMutableLd==null)
            positionMutableLd=new MutableLiveData<>();
        return positionMutableLd;
    }

    public void setPosition(int position){
        positionMutableLd.postValue(position);
    }


    WeatherRepository.GetWeatherData getWeatherData=new WeatherRepository.GetWeatherData() {
        @Override
        public void onResponse(Weather weather) {
            weatherMutableLD.postValue(weather);
            failedMutableLD.postValue(null);
        }

        @Override
        public void onFailure(String error) {
            weatherMutableLD.postValue(null);
            failedMutableLD.postValue(error);
        }

        @Override
        public void isLoading(Boolean isLoading) {
            if (isLoadingMutableLData==null)
                isLoadingMutableLData=new MutableLiveData<>();
            isLoadingMutableLData.postValue(isLoading);
        }
    };
}
