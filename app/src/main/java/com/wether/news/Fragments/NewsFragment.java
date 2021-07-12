package com.wether.news.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.wether.news.Adopters.NewsAdapter;
import com.wether.news.R;
import com.wether.news.ViewModels.NewsViewModel;
import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    //private List<NewsModel> newsModels;
    private TextView prev,next,newsTopic;
    private static int pos=0;
    private ProgressDialog progressDialog;
    private NewsViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_news, container, false);
        initViews(view);
        assert getArguments() != null;
        ArrayList<String> topics=getArguments().getStringArrayList("key");
        pos=getArguments().getInt("pos",0);
        newsTopic.setText(topics.get(pos));
        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);


        viewModel.getNewsModels(topics.get(pos),0).observe(getViewLifecycleOwner(),newsModels -> {
            Log.v("tag","Changes Observed");
            if (newsModels!=null){
                adapter.setNewsModels(newsModels);
                adapter.notifyDataSetChanged();
            }
            //Log.v("tag",newsModels.get(0).getDescription());
        });
        adapter=new NewsAdapter(viewModel.getNewsModels(topics.get(pos),0).getValue(),getContext());
        recyclerView.setAdapter(adapter);
        viewModel.isLoading().observe(getViewLifecycleOwner(),isLoading->{
            if (isLoading)
                showProgressDialog();
            else
                hideProgressDialog();
        });

        viewModel.onFailureData().observe(getViewLifecycleOwner(),error->{
            if (error!=null)
            Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();

        });
        prev.setOnClickListener(view1 -> {
            if (pos>0){
                pos--;
                newsTopic.setText(topics.get(pos));
                viewModel.getNewsModels(topics.get(pos),1).observe(getViewLifecycleOwner(),newsModels1 -> adapter.notifyDataSetChanged());
            }
        });
        next.setOnClickListener(view1 -> {
            if (pos<topics.size()-1){
                pos++;
                Log.v("tag","size=="+topics.size()+"current=="+pos);
                newsTopic.setText(topics.get(pos));
                viewModel.getNewsModels(topics.get(pos),1).observe(getViewLifecycleOwner(),newsModels1 -> adapter.notifyDataSetChanged());
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


    }



    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        if (progressDialog!=null)
        progressDialog.dismiss();
    }

}