package com.example.omtiamt.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        btnSaveUser = view.findViewById(R.id.signup_register_btn_id);
        inputusername = view.findViewById(R.id.username_register_id);
        inputpassword = view.findViewById(R.id.password_register_id);
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
        String name = inputusername.getText().toString();
        String password = inputpassword.getText().toString();
        String email = inputemail.getText().toString();
        Users user = new Users(name, password, email);
        model.instance.addUser(user, () -> {
            Navigation.findNavController(inputusername).navigateUp();
        });

    }
}