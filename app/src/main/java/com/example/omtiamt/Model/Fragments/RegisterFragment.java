package com.example.omtiamt.Model.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.R;


public class RegisterFragment extends Fragment {
    ImageButton btnSaveUser;
    EditText inputPassword;
    EditText inputConfirmPassword;
    EditText inputEmail;
    View view;
    Button backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        btnSaveUser = view.findViewById(R.id.signup_register_btn_id);
        inputPassword = view.findViewById(R.id.password_register_id);
        inputEmail = view.findViewById(R.id.email_register_id);
        inputConfirmPassword = view.findViewById(R.id.confirmpassword_register_id);
        backBtn = view.findViewById(R.id.register_back_btn);
        backBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment));

        btnSaveUser.setOnClickListener(v -> {
            // Checks if Email field is not empty
            if (TextUtils.isEmpty(inputEmail.getText().toString())) {
                inputEmail.setError("Email Field Cannot Be Empty");
                inputEmail.requestFocus();
            }
            if (model.instance.checkEmail(inputEmail.getText().toString())) {
                inputEmail.setError("Email already exist");
                inputEmail.requestFocus();
            }
            // Checks if Password field is not empty
            if (TextUtils.isEmpty(inputPassword.getText().toString())) {
                inputPassword.setError("Password Field Cannot Be Empty");
                inputPassword.requestFocus();
            }
            // Checks if Confirm Password field is not empty
            else if (TextUtils.isEmpty(inputConfirmPassword.getText().toString())) {
                inputConfirmPassword.setError("Confirm Password Field Cannot Be Empty");
                inputConfirmPassword.requestFocus();
            }
            // Checks for Password field length
            else if (inputPassword.getText().toString().length() < 6) {
                inputPassword.setError("Password must be 6 chars or longer");
                inputPassword.requestFocus();
            }
            // Checks if Email field is valid
            else if (!isValid(inputEmail.getText().toString())) {
                inputEmail.setError("Email is not Legal");
                inputEmail.requestFocus();
            }
            // Checks if Password and Confirm Password fields match
            else if
            (inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                model.instance.registerNewUser(inputEmail.getText().toString(), inputPassword.getText().toString());
                Toast.makeText(RegisterFragment.this.getContext(), "Register Successful", Toast.LENGTH_LONG).show();

                Navigation.findNavController(view).navigateUp();
            } else
                Toast.makeText(RegisterFragment.this.getContext(), "The Passwords don't match", Toast.LENGTH_LONG).show();
        });
        return view;
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}