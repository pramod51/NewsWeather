package com.wether.news.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wether.news.R;
import com.wether.news.ViewModels.WeatherViewModel;
import java.util.ArrayList;
public class WeatherFragment extends Fragment {


    ImageView imageView;
    TextView condition, location ,temperatureAndHumidity,airQuality,windAndCloud;
    private TextView prev,next,newsTopic;
    private int pos=0;
    private ProgressDialog progressDialog;
    private ArrayList<String> images = new ArrayList<>();
    private WeatherViewModel viewModel;
    private ArrayList<String> place;
    private Context context;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weather, container, false);
        assert getArguments() != null;
        place=getArguments().getStringArrayList("key");
        images=getArguments().getStringArrayList("urls");
        pos=getArguments().getInt("pos",0);

        initViews(view);
        context=getContext();
        viewModel=new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        if (viewModel.getPosition().getValue()!=null)
            pos=viewModel.getPosition().getValue();
        viewModel.getWeather(place.get(pos), pos).observe(getViewLifecycleOwner(),weather -> {
            if (weather==null)
                return;
            Glide.with(context).load(images.get(pos)).into(imageView);
            condition.setText(weather.getCurrent().getCondition().getText());
            location.setText(weather.getLocation().getName()+", "+weather.getLocation().getRegion());
            windAndCloud.setText(weather.getCurrent().getWindKph()+"KPH And "+weather.getCurrent().getCloud());

            temperatureAndHumidity.setText(weather.getCurrent().getTempC()+"C And "+weather.getCurrent().getHumidity());

            airQuality.setText("CO "+weather.getCurrent().getAirQuality().getCo()+
                    "\nNO2 "+weather.getCurrent().getAirQuality().getNo2()+
                    "\nO3 "+weather.getCurrent().getAirQuality().getO3()+
                    "\nSO2 "+weather.getCurrent().getAirQuality().getSo2()+
                    "\npm2_5 "+weather.getCurrent().getAirQuality().getPm25()+
                    "\npm10 "+weather.getCurrent().getAirQuality().getPm10()+
                    "\nus-epa-index "+weather.getCurrent().getAirQuality().getUsEpaIndex()+
                    "\ngb-defra-index "+weather.getCurrent().getAirQuality().getGbDefraIndex());

            hideProgressDialog();

        });

        viewModel.onFailureData().observe(getViewLifecycleOwner(),error->{
            if (error!=null)
                Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
        });
        viewModel.isLoading().observe(getViewLifecycleOwner(),isLoading->{
            if (isLoading)
                showProgressDialog();
            else
                hideProgressDialog();
        });
        viewModel.getPosition().observe(getViewLifecycleOwner(),pos-> newsTopic.setText(place.get(pos)));
        Log.v("tag","onCreate Called");


        prev.setOnClickListener(view1 -> {
            if (pos>0){
                pos--;
                newsTopic.setText(place.get(pos));
                viewModel.setPosition(pos);
                viewModel.getWeather(place.get(pos),pos );

            }
        });
        next.setOnClickListener(view1 -> {
            if (pos<place.size()-1){
                pos++;
                newsTopic.setText(place.get(pos));
                viewModel.setPosition(pos);
                viewModel.getWeather(place.get(pos),pos );
            }
        });

        return view;
    }
    private void initViews(View view){
        imageView=view.findViewById(R.id.image);
        condition=view.findViewById(R.id.condition);
        location=view.findViewById(R.id.location);
        temperatureAndHumidity=view.findViewById(R.id.temperature_and_humidity);
        windAndCloud=view.findViewById(R.id.wind_and_cloud);
        airQuality=view.findViewById(R.id.air_quality);
        prev=view.findViewById(R.id.prev);
        next=view.findViewById(R.id.next);
        newsTopic=view.findViewById(R.id.news_topic);
    }


    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        if (progressDialog!=null)
        progressDialog.dismiss();
    }

}