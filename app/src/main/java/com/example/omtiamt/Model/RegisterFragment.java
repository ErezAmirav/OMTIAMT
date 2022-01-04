package com.example.omtiamt.Model;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.omtiamt.Login;
import com.example.omtiamt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    ImageButton btnSaveUser;
    EditText inputPassword;
    EditText inputConfirmPassword;
    EditText inputEmail;
    View view;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();

        view = inflater.inflate(R.layout.fragment_register, container, false);
        btnSaveUser = view.findViewById(R.id.signup_register_btn_id);
        inputPassword = view.findViewById(R.id.password_register_id);
        inputEmail = view.findViewById(R.id.email_register_id);
        inputConfirmPassword = view.findViewById(R.id.confirmpassword_register_id);
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.instance.registerNewUser(inputEmail.getText().toString(),inputPassword.getText().toString());
            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        //FirebaseAuth.getInstance().signOut();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
                Toast.makeText(RegisterFragment.this.getContext(), "Current User Online", Toast.LENGTH_LONG).show();
           // Navigation.findNavController(view).navigate(R.id.action_registerFragment2_to_login2);

        } else
            Toast.makeText(RegisterFragment.this.getContext(), "User Offline", Toast.LENGTH_LONG).show();
    }



    public void logout_btn2(View view){
        FirebaseAuth.getInstance().signOut();
        onStart();
    }
}


    /*
    String name = inputusername.getText().toString();
        String password = inputpassword.getText().toString();
        String email = inputemail.getText().toString();
        Users user = new Users(name, password, email);
        model.instance.addUser(user, () -> {
            Navigation.findNavController(inputusername).navigateUp();
     */
