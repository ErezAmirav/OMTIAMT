package com.example.omtiamt;

import static com.example.omtiamt.R.id.login_btn_id;
import static com.example.omtiamt.R.id.login_clickhere_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Context;
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


public class Login extends AppCompatActivity {
    TextView Register;
    EditText SignName;
    EditText SignPassword;
    ImageButton connect;
    BottomNavigationView navigationView;
    ModelLogin myML;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hide navbar
        //getSupportActionBar().hide();
        //hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ModelFirebase mf = new ModelFirebase();
        connect = findViewById(login_btn_id);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new homePageFragment();
                navigationView.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
            }
        });

        SignName = findViewById(R.id.username_id);
        SignPassword = findViewById(R.id.password_id);
        navigationView = findViewById(R.id.bottom_navigation_id);
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

        Register = findViewById(login_clickhere_id);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myML.signin(SignName.getText().toString(), SignPassword.getText().toString()) == true)
                {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.body_container, new RegisterFragment()).commit();
                }
                else
                {
                    Context context = Login.this;
                    CharSequence text = "The User Don't Found";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });

    }

    public void login_btn(View view) {
    }
}
