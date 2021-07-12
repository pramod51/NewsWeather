package com.wether.news.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wether.news.Adopters.NewsWeatherAdapter;
import com.wether.news.R;
import com.wether.news.ViewModels.NewsWeatherViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class WeatherPlaceFragment extends Fragment  {

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


        viewModel = new ViewModelProvider(this).get(NewsWeatherViewModel.class);
        showProgressDialog();
        viewModel.initWeather();
        viewModel.getNewsWeather().observe(getViewLifecycleOwner(), newsweatherModels -> {
            if (adapter!=null)
            adapter.notifyDataSetChanged();
            hideProgressDialog();
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
        dialog.show();
        dialog.findViewById(R.id.add_item).setOnClickListener(view -> {

            Date d = new Date();
            Map<String, Object> map=new HashMap<>();
            map.put("topic", Objects.requireNonNull(topic.getText()).toString());
            map.put("timeStamp",d.getTime());
            viewModel.getImages(Objects.requireNonNull(topic.getText()).toString().replace(" ","+"),map,"Weather");
            Log.v("tag",topic.getText().toString());
            dialog.dismiss();
        });
    }


    /*private void getImages(String searchQuery){
        showProgressDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        Call<Images> imagesCall=jsonPlaceHolder.getImage("22376788-945cce53de128247cfdd90851",searchQuery,"photo");
        imagesCall.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                hideProgressDialog();
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v("tag","enteres");
                Images images=response.body();
                assert images != null;
                if (images.getTotal()==0){
                    //Toast.makeText(getContext(),"Write meaningful",Toast.LENGTH_LONG).show();
                    return;
                }
                String imageUrl=images.getHits().get(0).getPreviewURL();
                addATopic(searchQuery,imageUrl);
                Log.v("tag",imageUrl);
            }

            @Override
            public void onFailure(Call<Images> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.v("tag",t.getMessage());
            }
        });
    }*/



    private void initViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        addNew=view.findViewById(R.id.add_new);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //List<NewsweatherModel> newsWeatherModels = new ArrayList<>();
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