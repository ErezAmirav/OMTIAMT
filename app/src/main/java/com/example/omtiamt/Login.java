package com.example.omtiamt;

import static com.example.omtiamt.R.id.login_clickhere_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.omtiamt.Model.RegisterFragment;

import java.util.Timer;
import java.util.TimerTask;


public class Login extends AppCompatActivity {
    TextView Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Register = findViewById(login_clickhere_id);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login, new RegisterFragment()).commit();
            }
        });





    }
}