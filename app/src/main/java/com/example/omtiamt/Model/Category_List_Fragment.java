package com.example.omtiamt.Model;

import android.media.metrics.Event;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omtiamt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class Category_List_Fragment extends Fragment {
    View view;
    List<String> catData;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    HashMap<String,String> catHash = new HashMap<String,String>();;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category__list_, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_Categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        getCatNameAndPictures();

        CategorylistAdapter categorylistAdapter = new CategorylistAdapter(catHash);
        recyclerView.setAdapter(categorylistAdapter);
        return view;
    }
    public interface MyCallback {
        void onCallback(List<Event> eventList);
    }
    public void getCatNameAndPictures(  ) {
        CollectionReference applicationsRef = rootRef.collection("Category");
        rootRef.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        DocumentReference applicationIdRef = applicationsRef.document(id);
                        String name = document.getString("Name");
                        String picture = document.getString("Picture");
                        catHash.put(name, picture);
                    }

                }
            }

}