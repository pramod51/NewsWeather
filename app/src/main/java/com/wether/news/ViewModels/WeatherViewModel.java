package com.wether.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wether.news.Repositories.WeatherRepository;
import com.wether.news.WetherApi.Weather;


public class WeatherViewModel extends ViewModel implements WeatherRepository.GetWeatherData {

    private MutableLiveData<Weather> mutableLiveData;
    private final MutableLiveData<String> failedLiveData =new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingMutableLiveData;
    private MutableLiveData<Integer> posMutableLd;
    private WeatherRepository weatherRepository;

    public LiveData<Weather> getWeather(String search,int pos){

        if (mutableLiveData==null) {
            mutableLiveData=new MutableLiveData<>();
            weatherRepository=new WeatherRepository();
            weatherRepository.getWeatherData(search,this);

        }
        if (getPosition().getValue()!=null&&getPosition().getValue() != pos) {
            weatherRepository.getWeatherData(search,this);
            Log.v("tag","position=="+pos+"pos=="+getPosition().getValue());
        }

        return mutableLiveData;
    }
    public LiveData<String> onFailureData(){
        return failedLiveData;
    }
    public LiveData<Boolean> isLoading(){
        return isLoadingMutableLiveData;
    }
    public LiveData<Integer> getPosition(){
        if (posMutableLd==null)
            posMutableLd=new MutableLiveData<>();
        return posMutableLd;
    }

    public void setPosition(int position){
        if (posMutableLd==null)
            posMutableLd=new MutableLiveData<>();
        posMutableLd.postValue(position);
    }

    @Override
    public void onResponse(Weather weather) {
        mutableLiveData.postValue(weather);
        failedLiveData.postValue(null);
    }

    @Override
    public void onFailure(String error) {
        mutableLiveData.postValue(null);
        failedLiveData.postValue(error);
    }

    @Override
    public void isLoading(Boolean isLoading) {
        if (isLoadingMutableLiveData==null)
            isLoadingMutableLiveData=new MutableLiveData<>();
        isLoadingMutableLiveData.postValue(isLoading);
    }

}
