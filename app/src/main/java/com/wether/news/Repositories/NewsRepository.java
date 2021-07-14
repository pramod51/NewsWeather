package com.wether.news.Repositories;

import android.util.Log;

import com.wether.news.Models.NewsModel;
import com.wether.news.NewsApi.Article;
import com.wether.news.NewsApi.News;
import com.wether.news.NewsApi.NewsJsonPlaceHolder;
import com.wether.news.NewsApi.NewsRetroInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wether.news.Constants.getTodayDate;

public class NewsRepository {

    public interface GetNewsData{
        void onResponse(List<NewsModel> newsModels);
        void onFailure(String error);
    }

    public void getNewsContent(String searchQuery,GetNewsData getNewsData){
        Log.v(NewsRepository.class.getSimpleName(),"Getting News Data");

        NewsJsonPlaceHolder jsonPlaceHolder = NewsRetroInstance.getRetrofitClient().create(NewsJsonPlaceHolder.class);

        Call<News> newsCall=jsonPlaceHolder.getNews(searchQuery,getTodayDate(),"popularity","098726e5133c4174957984c339a8c150");
        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (!response.isSuccessful()){
                    getNewsData.onFailure("Something went wrong");
                    return;
                }
                Log.v(NewsRepository.class.getSimpleName(),"response Success");
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
                getNewsData.onFailure(t.getMessage());
                Log.v(NewsRepository.class.getSimpleName(),t.getMessage());
            }
        });
    }


}
