package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import com.wether.news.Fragments.NewsFragment;
import com.wether.news.Fragments.WeatherFragment;
import com.wether.news.R;

import java.util.ArrayList;


public class NewsWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_weather);
        //ActivityViewModel viewModel= new ViewModelProvider(this).get(ActivityViewModel.class);


        Log.v("tag","Activity oncre"+this);

        String type=getIntent().getStringExtra("type");
        Fragment fragment;
        ArrayList<String> topics=getIntent().getStringArrayListExtra("key");
        int pos=getIntent().getIntExtra("pos",0);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("key",topics);
        bundle.putStringArrayList("urls",getIntent().getStringArrayListExtra("urls"));
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