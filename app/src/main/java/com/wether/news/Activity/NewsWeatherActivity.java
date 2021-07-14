package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.wether.news.Constants;
import com.wether.news.Fragments.NewsFragment;
import com.wether.news.Fragments.WeatherFragment;
import com.wether.news.R;
import com.wether.news.databinding.ActivityNewsWeatherBinding;


public class NewsWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityNewsWeatherBinding.inflate(getLayoutInflater()).getRoot());


        Log.v(NewsWeatherActivity.class.getSimpleName(),"Activity Created");

        String type=getIntent().getStringExtra(Constants.TYPE);
        Fragment fragment;
        Bundle bundle=new Bundle();
        bundle.putStringArrayList(Constants.TOPICS,getIntent().getStringArrayListExtra(Constants.TOPICS));
        bundle.putStringArrayList(Constants.IMAGE_URLS,getIntent().getStringArrayListExtra(Constants.IMAGE_URLS));
        bundle.putInt(Constants.POSITION,getIntent().getIntExtra(Constants.POSITION,0));

        if (type.equals(getString(R.string.news))){
            fragment=new NewsFragment();
        }else {
            fragment=new WeatherFragment();

        }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }



}