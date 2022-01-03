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
                createUser();
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

    private void createUser(){

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            inputEmail.setError("Email Field Cannot Be Empty");
            inputEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            inputPassword.setError("Password Field Cannot Be Empty");
            inputPassword.requestFocus();
        }
        else if (TextUtils.isEmpty(confirmPassword)){
            inputConfirmPassword.setError("Confirm Password Field Cannot Be Empty");
            inputConfirmPassword.requestFocus();
        }

        if (password.equals(confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Executor) this, task -> {
                        if (task.isSuccessful()) {
                        //    FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterFragment.this.getContext(), Login.class));
                        } else
                            Toast.makeText(RegisterFragment.this.getContext(), "Register Error:" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
