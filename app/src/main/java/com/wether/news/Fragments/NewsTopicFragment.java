package com.wether.news.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wether.news.Adopters.NewsWeatherAdapter;
import com.wether.news.ImageApi.Images;
import com.wether.news.ImageApi.JsonPlaceHolder;
import com.wether.news.Models.NewsweatherModel;
import com.wether.news.R;
import com.wether.news.ViewModels.NewsWeatherViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsTopicFragment extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NewsweatherModel> newsWeatherModels;
    private ExtendedFloatingActionButton addNew;
    private String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private NewsWeatherViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_topic_weather_place, container, false);

        viewModel = new ViewModelProvider(this).get(NewsWeatherViewModel.class);
        viewModel.init();
        viewModel.getNewsWeather().observe(getActivity(), new Observer<List<NewsweatherModel>>() {
            @Override
            public void onChanged(List<NewsweatherModel> newsweatherModels) {
                if (adapter!=null)
                adapter.notifyDataSetChanged();
            }
        });

        initViews(view);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });




        return view;
    }


    private void openDialog(){
        //View view=LayoutInflater.from(getContext()).inflate(R.layout.popup_layout,null, false);
        //AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        //dialog.setView(view);
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_layout);
        TextInputEditText topic=(TextInputEditText) dialog.findViewById(R.id.add);
        topic.setHint("Enter Topic");
        dialog.show();
        dialog.findViewById(R.id.add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages(topic.getText().toString().replace(" ","+"));
                Log.v("tag",topic.getText().toString());
                dialog.dismiss();
            }
        });
    }


    private void getImages(String searchQuery){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        Call<Images> imagesCall=jsonPlaceHolder.getImage("22376788-945cce53de128247cfdd90851",searchQuery,"photo");
        imagesCall.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v("tag","enteres");
                Images images=response.body();
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
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.v("tag",t.getMessage());
            }
        });
    }

    private void addATopic(String topicName,String url){
        Date d = new Date();
        Map<String, Object> map=new HashMap<>();
        map.put("topic",topicName);
        map.put("timeStamp",d.getTime());
        map.put("imageUrl",url);
        FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child("News").push().setValue(map);
    }

    private void initViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        addNew=view.findViewById(R.id.add_new);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsWeatherModels=new ArrayList<>();
        //adapter=new NewsWeatherAdapter(newsWeatherModels,getContext());
        adapter=new NewsWeatherAdapter(viewModel.getNewsWeather().getValue(),getContext());
        recyclerView.setAdapter(adapter);


    }


}