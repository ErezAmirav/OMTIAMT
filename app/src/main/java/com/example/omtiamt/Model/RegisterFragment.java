package com.example.omtiamt.Model;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

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

import java.util.Objects;
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
                save3();
            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment2_to_login2);

        }
    }

    private void save3(){

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (password.equals(confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Executor) this, task -> {
                        if (task.isSuccessful()) {
                            Navigation.findNavController(inputEmail).navigateUp();
                        } else
                            Toast.makeText(RegisterFragment.this.getContext(), "Register Failed", Toast.LENGTH_LONG).show();
                    });
        } else
            Toast.makeText(RegisterFragment.this.getContext(), "Password Does not Match", Toast.LENGTH_LONG).show();
    }

    //------------------------------------------------------------------------//

    private void save1() {
        mAuth.createUserWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString())
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterFragment.this.getContext(), "Register Failed", Toast.LENGTH_LONG).show();
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterFragment.this.getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //------------------------------------------------------------------------//

    private void save2() {

        mAuth.createUserWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString()).
                    addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Navigation.findNavController(inputEmail).navigateUp();
                            } else {
                                Toast.makeText(RegisterFragment.this.getContext(), "Register Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
