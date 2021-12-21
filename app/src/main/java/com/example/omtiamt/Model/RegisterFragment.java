package com.example.omtiamt.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
    private DatabaseReference UserRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        btnSaveUser = (ImageButton) view.findViewById(R.id.signup_register_btn_id);
        EditText inputusername = (EditText) view.findViewById(R.id.username_register_id);
        EditText inputpassword = (EditText) view.findViewById(R.id.password_register_id);
        EditText inputconfirmpassword = (EditText) view.findViewById(R.id.confirmpassword_register_id);
        EditText inputemail = (EditText) view.findViewById(R.id.email_register_id);
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        btnSaveUser.setOnClickListener((v -> {
            String txtUserName = (inputusername.getText().toString());
            String txtPassword = (inputpassword.getText().toString());
            String txtConfirmPassword = (inputconfirmpassword.getText().toString());
            String txtEmail = (inputemail.getText().toString());
            Users user = new Users(txtUserName,txtPassword,txtEmail);
            UserRef.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(),"succses", Toast.LENGTH_SHORT).show();
                }
            });
            Navigation.findNavController(v).navigate(R.id.action_registerFragment2_to_login);
            //Navigation.findNavController(v).navigateUp();
        }));
        return view;

    }


}