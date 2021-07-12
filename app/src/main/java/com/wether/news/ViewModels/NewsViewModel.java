package com.wether.news.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wether.news.Models.NewsModel;
import com.wether.news.Repositories.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel implements NewsRepository.GetNewsData {

    private MutableLiveData<List<NewsModel>> mutableLiveData;
    private final NewsRepository newsRepository=new NewsRepository();
    private final MutableLiveData<String> failedLiveData =new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingMutableLd =new MutableLiveData<>();


    public LiveData<List<NewsModel>> getNewsModels(String search ,int x){
        if (mutableLiveData==null) {
            mutableLiveData=new MutableLiveData<>();
            newsRepository.getNewsContent(search,this);
        }
        if (x==1)
            newsRepository.getNewsContent(search,this);
        return mutableLiveData;
    }
    public LiveData<Boolean> isLoading(){
        return isLoadingMutableLd;
    }
    public LiveData<String> onFailureData(){
        return failedLiveData;
    }

    @Override
    public void onResponse(List<NewsModel> articleList) {
        mutableLiveData.postValue(articleList);
        failedLiveData.postValue(null);
    }

    @Override
    public void onFailure(String error) {
        mutableLiveData.postValue(null);
        failedLiveData.postValue(error);
    }

    @Override
    public void isLoading(Boolean isLoading) {
        isLoadingMutableLd.postValue(isLoading);
    }

}
