package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import com.wether.news.R;
import com.wether.news.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setUpNavigation();
        Log.v(MainActivity.class.getSimpleName(),"MainActivity Created");


    }
    private void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        //appBarConfiguration = new AppBarConfiguration.Builder(R.id.libraryFragment, R.id.readingFragment).build();
        NavigationUI.setupWithNavController(activityMainBinding.bottomNavView, navController);
        //NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);


    }



    /*@Override
    public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}
/*
* call Network in Repo only
* small firebase-
* Tag mess-
* Consts class
* static method
* varible name read
* Ratrofit genral
* Live data kewl viewModel
* enum use use
* ViewBinding
*
* */