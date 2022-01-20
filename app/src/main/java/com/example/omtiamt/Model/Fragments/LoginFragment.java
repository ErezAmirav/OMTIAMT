package com.example.omtiamt.Model.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.omtiamt.Model.Activity.BaseActivity;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    View view;
    TextView register;
    EditText signName;
    EditText signPassword;
    ImageButton connect;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

@Override
    public void onStart(){
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homePageFragment);
            }
        }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onResume() {
        super.onResume();
        BaseActivity.hideTabBar();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //onStart();
        view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        signName = view.findViewById(R.id.username_id);
        signPassword = view.findViewById(R.id.password_id);
        register = view.findViewById(R.id.login_clickhere_id);
        register.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));
        connect = view.findViewById(R.id.login_btn_id);
        connect.setOnClickListener(v -> login());
        return view;
    }

    private void login() {
        String email = signName.getText().toString();
        String password = signPassword.getText().toString();
        model.instance.SignIn(email, password, message -> {
            switch (message) {
                case "SUCCESS":
                    Toast.makeText(this.getContext(), "Welcome Back " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homePageFragment);
                    break;
                case "ERROR_WRONG_PASSWORD":
                    signPassword.setError("Incorrect Email/Password");
                    signPassword.requestFocus();
                    break;
                case "ERROR_USER_NOT_FOUND":
                    signName.setError("Email doesn't exist");
                    signName.requestFocus();
                    break;
                case "ERROR_INVALID_EMAIL":
                    signName.setError("ERROR_INVALID_EMAIL");
                    signName.requestFocus();
                    break;
            }
        });
    }
}