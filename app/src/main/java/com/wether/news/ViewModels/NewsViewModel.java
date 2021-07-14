package com.wether.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wether.news.Models.NewsModel;
import com.wether.news.Repositories.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<NewsModel>> newsModelMutableLD;
    private NewsRepository newsRepository;
    private MutableLiveData<String> failedMutableLD;
    private MutableLiveData<Boolean> isLoadingMutableLd;
    private MutableLiveData<Integer> positionMutableLD;


    public LiveData<List<NewsModel>> getNewsModels(String search){
        if (isLoadingMutableLd==null) {
            init();
            Log.v(NewsViewModel.class.getSimpleName(),"Started getting data");
            isLoadingMutableLd.postValue(true);
            newsRepository.getNewsContent(search, getNewsData);
        }
        return newsModelMutableLD;
    }
    public void nextPrevNewsTopic(String search){
        init();
        isLoadingMutableLd.postValue(true);
        newsRepository.getNewsContent(search,getNewsData);
    }

    private void init(){
        if (newsRepository==null)
            newsRepository=new NewsRepository();
        if (newsModelMutableLD==null)
            newsModelMutableLD=new MutableLiveData<>();
        if (failedMutableLD==null)
            failedMutableLD=new MutableLiveData<>();
        if (isLoadingMutableLd==null)
            isLoadingMutableLd=new MutableLiveData<>();
        if (positionMutableLD==null)
            positionMutableLD=new MutableLiveData<>();
    }

    public LiveData<Boolean> isLoading(){
        return isLoadingMutableLd;
    }
    public LiveData<String> onFailureData(){
        return failedMutableLD;
    }
    public LiveData<Integer> getPosition(){
        if (positionMutableLD==null)
            positionMutableLD=new MutableLiveData<>();
        return positionMutableLD;
    }
    public void setPosition(int position){
        positionMutableLD.postValue(position);
    }

    private final NewsRepository.GetNewsData getNewsData=new NewsRepository.GetNewsData() {
        @Override
        public void onResponse(List<NewsModel> articleList) {
            newsModelMutableLD.postValue(articleList);
            failedMutableLD.postValue(null);
            isLoadingMutableLd.postValue(false);
        }

        @Override
        public void onFailure(String error) {
            newsModelMutableLD.postValue(null);
            failedMutableLD.postValue(error);
            isLoadingMutableLd.postValue(false);
        }

    };


}
