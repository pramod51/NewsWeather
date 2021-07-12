package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wether.news.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    //private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setUpNavigation();
        Log.v("tag","mainActivity");


    }
    private void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        //appBarConfiguration = new AppBarConfiguration.Builder(R.id.libraryFragment, R.id.readingFragment).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);


    }


    private void initViews() {
        bottomNavigationView=findViewById(R.id.bottom_nav_view);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}