package com.example.omtiamt.Model.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;

import android.os.Bundle;
import android.view.View;

import com.example.omtiamt.Model.Fragments.NewProductFragment;
import com.example.omtiamt.Model.Fragments.homePageFragment;
import com.example.omtiamt.Model.Fragments.ProfileFragment;
import com.example.omtiamt.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    NavController navCtl;
    public static BottomNavigationView navigationView;
    public static final BaseActivity instance = new BaseActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.base_navhost);
        navCtl = navHost.getNavController();
        navigationView = findViewById(R.id.bottom_navigation_id);
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new homePageFragment();
                    break;

                case R.id.nav_add:
                    fragment = new NewProductFragment();
                    break;

                case R.id.nav_profile:
                    fragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
            return true;
        });
    }
    public static void hideTabBar() {
        navigationView.setVisibility(View.GONE);
    }
    public static void showTabBar(){
        navigationView.setVisibility(View.VISIBLE);
    }
}