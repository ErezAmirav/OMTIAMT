package com.example.omtiamt.Model;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static Map<String, Object> UsersMap;
    static Map<String, Object> ProductMap;

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
        UsersMap.put("name", json.get("name"));
        UsersMap.put("id", json.get("id"));
        UsersMap.put("password", json.get("password"));
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
        UsersMap.put("name", json.get("name"));
        UsersMap.put("id", json.get("id"));
        UsersMap.put("password", json.get("password"));
        db.collection(Users.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public boolean canSignin(String signName, String signPass) {
        if (UsersMap.containsValue(signName)) {
            {
                String checkmyUserName = (String) UsersMap.get("name");
                String checkmyUserPassword = (String) UsersMap.get("password");
                if ((signName.equals(checkmyUserName)) && (signPass.equals(checkmyUserPassword))) {
                    return true;
                }
            }
        }
        return false;
    }


    public void getUsersById(String userId) {
    }

    public List<String> getCategoriesName() {
        List<String> list = new LinkedList<String>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://console.firebase.google.com/u/0/project/omtiamt-ie/firestore/data/Category/");
        // ^^^^^
        list.add(mRef.child("Category").orderByChild("name").toString());
        return list;
    }
}

