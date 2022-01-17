package com.example.omtiamt.Model;

import android.annotation.SuppressLint;
import android.media.metrics.Event;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.omtiamt.Login;
import com.example.omtiamt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category_List_Fragment extends Fragment {
    View view;
    RecyclerView catRV;
    HashMap<String,String> myCatHash = new HashMap<String,String>();
    CategorylistAdapter catAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category__list_, container, false);
        catRV = view.findViewById(R.id.recyclerview_Categories);
      //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
        catRV.setLayoutManager(layoutManager);

        model.instance.getCatNameAndPictures(myCatHash, catHash -> {
         catAdapter.setCategoryMap(catHash);
         catAdapter.notifyDataSetChanged();
        });

        catAdapter = new CategorylistAdapter();
        catRV.setAdapter(catAdapter);

        catAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                for (Map.Entry<String, String> entry : myCatHash.entrySet()) {
                    String tmp = (new ArrayList<>(myCatHash.keySet())).get(position);
                    Toast.makeText(view.getContext(), "Welcome Back " + tmp, Toast.LENGTH_LONG).show();

                }
            }
        });
        return view;
    }
}