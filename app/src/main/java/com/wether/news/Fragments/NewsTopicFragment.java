package com.wether.news.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wether.news.Adopters.NewsWeatherAdapter;
import com.wether.news.R;
import com.wether.news.ViewModels.NewsWeatherViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewsTopicFragment extends Fragment  {

    private NewsWeatherAdapter adapter;
    private ExtendedFloatingActionButton addNew;
    //private final String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private NewsWeatherViewModel viewModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_topic_weather_place, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(NewsWeatherViewModel.class);
        showProgressDialog();
        viewModel.init();
        viewModel.getNewsWeather().observe(getViewLifecycleOwner(), newsweatherModels -> {
            if (adapter!=null)
            adapter.notifyDataSetChanged();
        });
        viewModel.getIsUpdating().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                showProgressDialog();
            else
            hideProgressDialog();
        });
        initViews(view);
        addNew.setOnClickListener(view1 -> openDialog());




        return view;
    }


    private void openDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_layout);
        TextInputEditText topic=dialog.findViewById(R.id.add);
        topic.setHint("Enter Topic");
        dialog.show();
        dialog.findViewById(R.id.add_item).setOnClickListener(view -> {

            Date d = new Date();
            Map<String, Object> map=new HashMap<>();
            map.put("topic", Objects.requireNonNull(topic.getText()).toString());
            map.put("timeStamp",d.getTime());
            //FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child("News").push().setValue(map);
            viewModel.getImages(Objects.requireNonNull(topic.getText()).toString().replace(" ","+"),map,"News");
            Log.v("tag",topic.getText().toString());
            dialog.dismiss();
        });
    }




    private void initViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        addNew=view.findViewById(R.id.add_new);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        List<NewsweatherModel> newsWeatherModels = new ArrayList<>();
        //adapter=new NewsWeatherAdapter(newsWeatherModels,getContext());
        adapter=new NewsWeatherAdapter(viewModel.getNewsWeather().getValue(),getContext());
        recyclerView.setAdapter(adapter);


    }
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

}