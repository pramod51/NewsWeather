package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.wether.news.Fragments.NewsFragment;
import com.wether.news.Fragments.WeatherFragment;
import com.wether.news.R;
import com.wether.news.WetherApi.Weather;
import com.wether.news.WetherApi.WeatherJsonPlaceHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_weather);
        String type=getIntent().getStringExtra("type");
        Fragment fragment = null;
        ArrayList<String> topics=getIntent().getStringArrayListExtra("key");
        int pos=getIntent().getIntExtra("pos",0);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("key",topics);
        bundle.putInt("pos",pos);
        if (type.equals("News")){
            fragment=new NewsFragment();

        }else {
            fragment=new WeatherFragment();

        }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }



}