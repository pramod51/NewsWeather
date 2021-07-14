package com.wether.news.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wether.news.Constants;
import com.wether.news.R;
import com.wether.news.ViewModels.WeatherViewModel;
import com.wether.news.WetherApi.Weather;
import com.wether.news.databinding.FragmentWeatherBinding;

import java.util.ArrayList;
public class WeatherFragment extends Fragment {

    private int position=0;
    private ProgressDialog progressDialog;
    private ArrayList<String> images = new ArrayList<>();
    private WeatherViewModel viewModel;
    private ArrayList<String> place;
    private Context context;
    private FragmentWeatherBinding weatherBinding;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weatherBinding=FragmentWeatherBinding.inflate(inflater, container, false);
        View view= weatherBinding.getRoot();

        assert getArguments() != null;
        place=getArguments().getStringArrayList(Constants.TOPICS);
        images=getArguments().getStringArrayList(Constants.IMAGE_URLS);
        position=getArguments().getInt(Constants.POSITION,0);


        context=getContext();
        viewModel=new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        if (viewModel.getPosition().getValue()==null)
            viewModel.setPosition(position);


        viewModel.getWeather(place.get(position)).observe(getViewLifecycleOwner(), this::getWeather);

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

        viewModel.getPosition().observe(getViewLifecycleOwner(),position-> weatherBinding.weatherPlace.setText(place.get(position)));
        Log.v(WeatherFragment.class.getSimpleName(),"onCreate Called");


        weatherBinding.prev.setOnClickListener(view1 -> {
            if (position>0){
                position--;
                viewModel.setPosition(position);
                weatherBinding.weatherPlace.setText(place.get(position));
                viewModel.nextPrevWeatherUpdate(place.get(position));

            }
        });
        weatherBinding.next.setOnClickListener(view1 -> {
            if (position<place.size()-1){
                position++;
                viewModel.setPosition(position);
                weatherBinding.weatherPlace.setText(place.get(position));
                viewModel.nextPrevWeatherUpdate(place.get(position));
            }
        });

        return view;
    }



    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        if (progressDialog!=null)
        progressDialog.dismiss();
    }

    @SuppressLint("SetTextI18n")
    private void getWeather(Weather weather) {
        if (weather == null)
            return;
        Glide.with(context).load(images.get(position)).into(weatherBinding.image);
        weatherBinding.condition.setText(weather.getCurrent().getCondition().getText());
        weatherBinding.location.setText(weather.getLocation().getName() + ", " + weather.getLocation().getRegion());
        weatherBinding.windAndCloud.setText(weather.getCurrent().getWindKph() + "KPH And " + weather.getCurrent().getCloud());

        weatherBinding.temperatureAndHumidity.setText(weather.getCurrent().getTempC() + "C And " + weather.getCurrent().getHumidity());

        weatherBinding.airQuality.setText("CO " + weather.getCurrent().getAirQuality().getCo() +
                "\nNO2 " + weather.getCurrent().getAirQuality().getNo2() +
                "\nO3 " + weather.getCurrent().getAirQuality().getO3() +
                "\nSO2 " + weather.getCurrent().getAirQuality().getSo2() +
                "\npm2_5 " + weather.getCurrent().getAirQuality().getPm25() +
                "\npm10 " + weather.getCurrent().getAirQuality().getPm10() +
                "\nus-epa-index " + weather.getCurrent().getAirQuality().getUsEpaIndex() +
                "\ngb-defra-index " + weather.getCurrent().getAirQuality().getGbDefraIndex());

        hideProgressDialog();

    }
}