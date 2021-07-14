package com.wether.news.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.wether.news.Adopters.NewsAdapter;
import com.wether.news.Constants;
import com.wether.news.R;
import com.wether.news.ViewModels.NewsViewModel;
import com.wether.news.databinding.FragmentNewsBinding;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private NewsAdapter adapter;
    private static int position=0;
    private ProgressDialog progressDialog;
    private NewsViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentNewsBinding newsBinding=FragmentNewsBinding.inflate(inflater,container,false);
        View view= newsBinding.getRoot();

        newsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assert getArguments() != null;
        ArrayList<String> topics=getArguments().getStringArrayList(Constants.TOPICS);
        position=getArguments().getInt(Constants.POSITION,0);

        newsBinding.newsTopic.setText(topics.get(position));
        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        if (viewModel.getPosition().getValue()==null)
            viewModel.setPosition(position);

        viewModel.getPosition().observe(getViewLifecycleOwner(),position-> newsBinding.newsTopic.setText(topics.get(position)));

        viewModel.getNewsModels(topics.get(position)).observe(getViewLifecycleOwner(),newsModels -> {
            Log.v(NewsFragment.class.getSimpleName(),"Changes Observed");
            if (newsModels!=null){
                adapter.setNewsModels(newsModels);
                Log.v(NewsFragment.class.getSimpleName(),"Data set to News Model class");
            }
        });
        adapter=new NewsAdapter(viewModel.getNewsModels(topics.get(position)).getValue(),getContext());
        newsBinding.recyclerView.setAdapter(adapter);
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
        newsBinding.prev.setOnClickListener(view1 -> {
            if (position>0){
                position--;
                viewModel.setPosition(position);
                viewModel.nextPrevNewsTopic(topics.get(position));
            }
        });
        newsBinding.next.setOnClickListener(view1 -> {
            if (position<topics.size()-1){
                position++;
                viewModel.setPosition(position);
                viewModel.nextPrevNewsTopic(topics.get(position));
            }
        });



        return view;
    }





    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        if (progressDialog!=null)
        progressDialog.dismiss();
    }

}