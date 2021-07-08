package com.wether.news.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wether.news.R;
import com.wether.news.WetherApi.Weather;
import com.wether.news.WetherApi.WeatherJsonPlaceHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment {

    ImageView imageView;
    TextView condition, location ,temperatureAndHumidity,airQuality,windAndCloud;
    private TextView prev,next,newsTopic;
    private int pos=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weather, container, false);
        ArrayList<String> topics=getArguments().getStringArrayList("key");
        pos=getArguments().getInt("pos",0);
        initViews(view);
        getWeather(topics.get(pos));
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos>0){
                    pos--;
                    getWeather(topics.get(pos));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos<topics.size()){
                    pos++;
                    getWeather(topics.get(pos));
                }
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
    private void getWeather(String searchQuery){
        newsTopic.setText(searchQuery);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherJsonPlaceHolder jsonPlaceHolder = retrofit.create(WeatherJsonPlaceHolder.class);

        Call<Weather> weatherCall=jsonPlaceHolder.getWeather("31190e610bb04c82b42123859210707",searchQuery,"yes");
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v("tag","entered");
                Weather weather=response.body();
                if (weather==null)
                    return;
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





            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.v("tag",t.getMessage());
            }
        });
    }
}