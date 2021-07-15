package com.wether.news.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wether.news.Api.JsonPlaceHolder;
import com.wether.news.Api.RetroInstances;
import com.wether.news.Constants;
import com.wether.news.Models.NewsModel;
import com.wether.news.Api.News;
import com.wether.news.Models.NewsWeatherModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static com.wether.news.Constants.getTodayDate;

public class NewsRepository {

    public interface FirebaseNewsTopics{
        void onFailure(String error);
        void responseData(List<NewsWeatherModel> newsWeatherModels);
    }


    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private static NewsRepository instance;
    List<NewsWeatherModel> newsWeather=new ArrayList<>();


    public static NewsRepository getInstance(){
        if (instance==null){
            instance=new NewsRepository();
        }
        return instance;
    }

    public void setNewsTopics(NewsRepository.FirebaseNewsTopics firebaseNewsTopics){
        FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(uId).child(Constants.NEWS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newsWeather.clear();
                        if (snapshot.exists())
                            for (DataSnapshot ds:snapshot.getChildren()){
                                newsWeather.add(new NewsWeatherModel(ds.child(Constants.TOPICS).getValue(String.class),
                                        Constants.getTimeAgo(Long.parseLong(""+ds.child(Constants.TIME_STAMP).getValue())),
                                        ds.child(Constants.IMAGE_URL).getValue(String.class),"News",ds.getKey()));
                            }
                        Log.v(NewsRepository.class.getName(),"NewsTopics Loaded");
                        firebaseNewsTopics.responseData(newsWeather);
                        firebaseNewsTopics.onFailure(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseNewsTopics.onFailure(error.getMessage());
                    }
                });

    }




    public interface GetNewsData{
        void onResponse(List<NewsModel> newsModels);
        void onFailure(String error);
    }

    public void getNewsContent(String searchQuery,GetNewsData getNewsData){
        Log.v(NewsRepository.class.getSimpleName(),"Getting News Data");

        JsonPlaceHolder jsonPlaceHolder = RetroInstances.getNewsRetrofitClient().create(JsonPlaceHolder.class);

        Call<News> newsCall=jsonPlaceHolder.getNews(searchQuery,getTodayDate(),"popularity","098726e5133c4174957984c339a8c150");
        newsCall.enqueue(new Callback<News>() {
            @EverythingIsNonNull
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
                for (News.Article article:news.getArticles()){
                    newsModels.add(new NewsModel(article.getTitle(),article.getDescription(),article.getPublishedAt(),article.getSource().getName(),
                            article.getUrlToImage()));
                }
                getNewsData.onResponse(newsModels);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                getNewsData.onFailure(t.getMessage());
                Log.v(NewsRepository.class.getSimpleName(),t.getMessage());
            }
        });
    }



}
