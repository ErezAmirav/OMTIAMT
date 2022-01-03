package com.example.omtiamt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.omtiamt.Model.RegisterFragment;
import com.example.omtiamt.Model.TestActivity;
import com.example.omtiamt.Model.homePageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;


public class Loading_Screen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        onStart();



    }

    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }, 4000);
        } else {
           // getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new homePageFragment()).commit();
            startActivity(new Intent(getApplicationContext(), TestActivity.class));

        }
    }
}