package com.wether.news.Repositories;

import android.text.format.DateFormat;
import android.util.Log;

import com.wether.news.Models.NewsModel;
import com.wether.news.NewsApi.Article;
import com.wether.news.NewsApi.News;
import com.wether.news.NewsApi.NewsJsonPlaceHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {

    public interface GetNewsData{
        void onResponse(List<NewsModel> newsModels);
        void onFailure(String error);
        void isLoading(Boolean isLoading);
    }

    public void getNewsContent(String searchQuery,GetNewsData getNewsData){
        getNewsData.isLoading(true);
        Log.v("tag","enter");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsJsonPlaceHolder jsonPlaceHolder = retrofit.create(NewsJsonPlaceHolder.class);

        Call<News> newsCall=jsonPlaceHolder.getNews(searchQuery,getTodayDate(),"popularity","098726e5133c4174957984c339a8c150");
        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                getNewsData.isLoading(false);
                if (!response.isSuccessful()){
                    getNewsData.onFailure("Something went wrong");
                    return;
                }
                Log.v("tag","response Success");
                News news=response.body();
                assert news != null;
                if (news.getTotalResults()==0) {
                    getNewsData.onFailure("No Result Found");
                    return;
                }
                List<NewsModel> newsModels=new ArrayList<>();
                for (Article article:news.getArticles()){
                    newsModels.add(new NewsModel(article.getTitle(),article.getDescription(),article.getPublishedAt(),article.getSource().getName(),
                            article.getUrlToImage()));
                }
                getNewsData.onResponse(newsModels);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                getNewsData.onFailure(t.getMessage());
                getNewsData.isLoading(false);
                Log.v("tag",t.getMessage());
            }
        });
    }

    public String getTodayDate(){

        Date d = new Date();
        return DateFormat.format("yyyy-MM-dd", d.getTime()).toString();
    }
}
