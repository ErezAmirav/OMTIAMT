package com.example.omtiamt.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Update;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.omtiamt.Login;
import com.example.omtiamt.Model.model;
import com.example.omtiamt.Model.Users;
import com.example.omtiamt.Model.ModelFirebase;
import com.example.omtiamt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    ImageButton btnSaveUser;
    EditText nameEt;
    EditText inputusername;
    EditText inputpassword;
    EditText inputconfirmpassword;
    EditText inputemail;
    View view;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_register, container, false);
        btnSaveUser = view.findViewById(R.id.signup_register_btn_id);
        inputusername = view.findViewById(R.id.username_register_id);
        inputconfirmpassword = view.findViewById(R.id.confirmpassword_register_id);
        inputemail = view.findViewById(R.id.email_register_id);
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        return view;
    }

    private void save() {
        inputpassword = view.findViewById(R.id.password_register_id);
        inputemail = view.findViewById(R.id.email_register_id);
        inputconfirmpassword = view.findViewById(R.id.confirmpassword_register_id);
        String password = inputpassword.getText().toString();
        String confirmpassword = inputconfirmpassword.getText().toString();
        String email = inputemail.getText().toString();
        if(password == confirmpassword) {
            mAuth.createUserWithEmailAndPassword
                    (email, password).
                    addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Navigation.findNavController(inputusername).navigateUp();
                            } else {
                                Toast.makeText(RegisterFragment.this.getContext(), "Register Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(RegisterFragment.this.getContext(), "The password not Equals", Toast.LENGTH_LONG).show();
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
}