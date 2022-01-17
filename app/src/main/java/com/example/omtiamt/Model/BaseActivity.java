package com.example.omtiamt.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.omtiamt.R;

public class BaseActivity extends AppCompatActivity {
    NavController navCtl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.base_navhost);
        navCtl = navHost.getNavController();

    }
}