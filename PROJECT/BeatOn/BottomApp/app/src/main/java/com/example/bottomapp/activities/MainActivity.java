package com.example.bottomapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.bottomapp.helpers.Databaser;
import com.example.bottomapp.R;
import com.example.bottomapp.TypefaceUtil;
import com.example.bottomapp.User;
import com.example.bottomapp.ui.AddFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public static User currentUser;

    public Databaser databaser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaser = new Databaser();

        TypefaceUtil.overrideFont(getApplicationContext(), "SANS_SERIF", "@font/montserrat");
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile, R.id.navigation_feed, R.id.navigation_add)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AddFragment.setImageButton(requestCode, resultCode, data, this);
        AddFragment.setBeat(requestCode, resultCode, data, this);
    }
}