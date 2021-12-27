package com.example.omtiamt;

import com.example.omtiamt.Model.ModelFirebase;
import com.example.omtiamt.Model.Users;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class ModelLogin {
    ModelFirebase Myref = new ModelFirebase();

    public boolean signin(String name, String password)
    {
        return Myref.canSignin(name,password);

    }

}
