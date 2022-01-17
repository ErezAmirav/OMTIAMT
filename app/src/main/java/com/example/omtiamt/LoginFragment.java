package com.example.omtiamt;

import static com.example.omtiamt.R.id.login_clickhere_id;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Model.BaseActivity;
import com.example.omtiamt.Model.NewProductFragment;
import com.example.omtiamt.Model.ProfileFragment;
import com.example.omtiamt.Model.RegisterFragment;
import com.example.omtiamt.Model.homePageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    View view;
    TextView Register;
    EditText SignName;
    EditText SignPassword;
    ImageButton connect;
    BottomNavigationView navigationView;
    ImageButton logout;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ImageButton test;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        SignName = view.findViewById(R.id.username_id);
        SignPassword = view.findViewById(R.id.password_id);
        Register = view.findViewById(R.id.login_clickhere_id);
        Register.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        connect = view.findViewById(R.id.login_btn_id);
        connect.setOnClickListener(v -> {
            login();
        });

         return view;
    }

    private void login() {
        String email = SignName.getText().toString();
        String password = SignPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this.getContext(), "Welcome Back " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homePageFragment);



            } else {
                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                switch (errorCode) {

                    case "ERROR_WRONG_PASSWORD":
                        SignPassword.setError("Incorrect Email/Password");
                        SignPassword.requestFocus();
                        break;

                    case "ERROR_USER_NOT_FOUND":
                        SignName.setError("Email doesn't exist");
                        SignName.requestFocus();
                        break;
                }
            }
        });
    }
}