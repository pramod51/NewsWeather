package com.wether.news.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.textfield.TextInputEditText;
import com.wether.news.Adopters.NewsWeatherAdapter;
import com.wether.news.Constants;
import com.wether.news.R;
import com.wether.news.ViewModels.NewsWeatherViewModel;
import com.wether.news.databinding.FragmentNewsTopicWeatherPlaceBinding;
import com.wether.news.databinding.PopupLayoutBinding;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class WeatherPlaceFragment extends Fragment  {

    private NewsWeatherAdapter adapter;
    //private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private NewsWeatherViewModel viewModel;
    private ProgressDialog progressDialog;
    private FragmentNewsTopicWeatherPlaceBinding newsTopicWeatherPlaceBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        newsTopicWeatherPlaceBinding= FragmentNewsTopicWeatherPlaceBinding.inflate(inflater,container,false);
        View view= newsTopicWeatherPlaceBinding.getRoot();

        viewModel = new ViewModelProvider(this).get(NewsWeatherViewModel.class);

        viewModel.initWeatherPLace();
        initViews();

        viewModel.getNewsWeather().observe(getViewLifecycleOwner(), newsWeatherModels -> {
            if (newsWeatherModels!=null)
                adapter.setNewsWeatherChanges(newsWeatherModels);
        });
        viewModel.getIsUpdating().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                showProgressDialog();
            else
                hideProgressDialog();
        });
        newsTopicWeatherPlaceBinding.addNew.setOnClickListener(view1 -> openDialog());




        return view;
    }


    private void openDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        PopupLayoutBinding popupLayoutBinding=PopupLayoutBinding.inflate(LayoutInflater.from(getContext()),null,false);
        dialog.setContentView(popupLayoutBinding.getRoot());
        popupLayoutBinding.add.setHint(R.string.enter_topic);
        dialog.show();
        popupLayoutBinding.addItem.setOnClickListener(view -> {

            Date d = new Date();
            Map<String, Object> map=new HashMap<>();
            map.put(Constants.TOPICS, Objects.requireNonNull(popupLayoutBinding.add.getText()).toString());
            map.put(Constants.TIME_STAMP,d.getTime());
            viewModel.getImages(Objects.requireNonNull(popupLayoutBinding.add.getText()).toString().replace(" ","+"),map,getString(R.string.weather));

            dialog.dismiss();
        });
    }



    private void initViews() {
        newsTopicWeatherPlaceBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new NewsWeatherAdapter(viewModel.getNewsWeather().getValue(),getContext());
        newsTopicWeatherPlaceBinding.recyclerView.setAdapter(adapter);


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
}