package com.wether.news.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wether.news.Constants;
import com.wether.news.Models.NewsWeatherModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsTopicRepo {

    public interface FirebaseNewsTopics{
        void onFailure(String error);
        void responseData(List<NewsWeatherModel> newsWeatherModels);
    }


    private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private static NewsTopicRepo instance;
    List<NewsWeatherModel> newsWeather=new ArrayList<>();


    public static NewsTopicRepo getInstance(){
        if (instance==null){
            instance=new NewsTopicRepo();
        }
        return instance;
    }

    public void setNewsTopics(FirebaseNewsTopics firebaseNewsTopics){
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
                        Log.v(NewsTopicRepo.class.getName(),"NewsTopics Loaded");
                            firebaseNewsTopics.responseData(newsWeather);
                            firebaseNewsTopics.onFailure(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        firebaseNewsTopics.onFailure(error.getMessage());
                    }
                });

    }




}
