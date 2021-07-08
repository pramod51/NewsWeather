package com.wether.news.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wether.news.Adopters.NewsAdapter;
import com.wether.news.Models.NewsModel;
import com.wether.news.NewsApi.Article;
import com.wether.news.NewsApi.News;
import com.wether.news.NewsApi.NewsJsonPlaceHolder;
import com.wether.news.R;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<NewsModel> newsModels;
    private TextView prev,next,newsTopic;
    private int pos=0;
    private final String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_news, container, false);
        initViews(view);
        ArrayList<String> topics=getArguments().getStringArrayList("key");
        pos=getArguments().getInt("pos",0);

        getNewsContent(topics.get(pos));
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos>0){
                    pos--;
                    getNewsContent(topics.get(pos));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos<topics.size()){
                    pos++;
                    getNewsContent(topics.get(pos));
                }
            }
        });




        return view;
    }

    private void initViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        prev=view.findViewById(R.id.prev);
        next=view.findViewById(R.id.next);
        newsTopic=view.findViewById(R.id.news_topic);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsModels=new ArrayList<>();
        adapter=new NewsAdapter(newsModels,getContext());
        recyclerView.setAdapter(adapter);

    }
    private void getNewsContent(String searchQuery){
        showProgressDialog();
        newsTopic.setText(searchQuery);
        newsModels.clear();
        Log.v("tag","enter");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsJsonPlaceHolder jsonPlaceHolder = retrofit.create(NewsJsonPlaceHolder.class);

        Call<News> newsCall=jsonPlaceHolder.getNews(searchQuery,getTodayDate(),"popularity","098726e5133c4174957984c339a8c150");
        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                hideProgressDialog();
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v("tag","");
                News news=response.body();
                if (news.getTotalResults()==0)
                    return;
                for (Article article:news.getArticles()){
                    newsModels.add(new NewsModel(article.getTitle(),article.getDescription(),article.getPublishedAt(),article.getSource().getName(),
                            article.getUrlToImage()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.v("tag",t.getMessage());
            }
        });
    }
    public String getTodayDate(){

        Date d = new Date();
        return DateFormat.format("yyyy-MM-dd", d.getTime()).toString();
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