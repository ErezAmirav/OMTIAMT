package com.example.omtiamt;

import static com.example.omtiamt.R.id.login_btn_id;
import static com.example.omtiamt.R.id.login_clickhere_id;
import static com.example.omtiamt.R.id.product_username_id;
import static com.example.omtiamt.R.id.username_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Model.ModelFirebase;
import com.example.omtiamt.Model.NewProductFragment;
import com.example.omtiamt.Model.ProductFragment;
import com.example.omtiamt.Model.ProfileFragment;
import com.example.omtiamt.Model.homePageFragment;
import com.example.omtiamt.Model.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class Login extends AppCompatActivity {
    TextView Register;
    EditText SignName;
    EditText SignPassword;
    ImageButton connect;
    BottomNavigationView navigationView;
    ImageButton logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hide navbar
        //getSupportActionBar().hide();
        //hide status bar
        mAuth = FirebaseAuth.getInstance();



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SignName = findViewById(R.id.username_id);
        SignPassword = findViewById(R.id.password_id);
        navigationView = findViewById(R.id.bottom_navigation_id);
  /*      connect = findViewById(login_btn_id);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Fragment fragment = new homePageFragment();
                    navigationView.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
            }
        });*/
        // getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new homePageFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setVisibility(View.GONE);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
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
            }
        });

        // Register Button
        Register = findViewById(login_clickhere_id);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.body_container, new RegisterFragment()).commit();
            }

        });
    }



    public void login_btn(View view) {
        String email = SignName.getText().toString();
        String password = SignPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.homePageFragment, new RegisterFragment()).commit();
                            Toast.makeText(Login.this, "Welcome Back", Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    });
        }




    public void logout_btn(View view) {
        mAuth.signOut();
        startActivity(new Intent(this, Login.class));
    }
}
