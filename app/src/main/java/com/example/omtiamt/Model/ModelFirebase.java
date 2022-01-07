package com.example.omtiamt.Model;


import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.omtiamt.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public void getAllUsers(model.GetAllUsersListener listener) {
        db.collection(Users.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<Users> list = new LinkedList<Users>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Users user = Users.create(doc.getData());
                            if (user != null)
                                list.add(user);
                        }
                    }
                    listener.onComplete(list);
                });

    }

    public void addUser(Users user, model.AddUsersListener listener) {
        Map<String, Object> json = user.toJson();
        String NewDocument = db.collection(Users.COLLECTION_NAME).document().getId().toString();
        json.put("id", NewDocument);
        db.collection(Users.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void addProduct(Product product, model.AddProductListener listener) {
        Map<String, Object> json = product.toJson();
        String NewDocument = db.collection(Users.COLLECTION_NAME).document().getId().toString();
        json.put("id", NewDocument);
        db.collection(Users.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void registerNewUser(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Tag", "Succses");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Log.w("Tag", "Fail");
                    }
                });
    }

    boolean check;
    boolean test;
    public void checkEmail(String email) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task ->{
            if(task.isSuccessful())
            check = !task.getResult().getSignInMethods().isEmpty();
            test2(check);
        });

    }

    public void test2(boolean check)
    {
        test = check;
    }
    public boolean test3 ()
    {
        return test;
    }

    public void getUsersById(String userId) {
    }

    public List<String> getCategoriesName() {
        List<String> list = new LinkedList<String>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://console.firebase.google.com/u/0/project/omtiamt-ie/firestore/data/Category/");
        // ^^^^^
        list.add(mRef.child("Category").orderByChild("Name").toString());

        return list;
    }

     String messeage = "success";
    public String loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    messeage = "success";
                } else {
                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                    switch (errorCode) {

                        case "ERROR_WRONG_PASSWORD":
                            messeage = "ERROR_WRONG_PASSWORD";
                            break;

                        case "ERROR_EMAIL_ALREADY_IN_USE":
                            messeage = "ERROR_EMAIL_ALREADY_IN_USE";
                            break;
                    }

                }
            }
        });
        return messeage;
    }
}