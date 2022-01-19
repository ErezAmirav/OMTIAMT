package com.example.omtiamt.Model.Recylers;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

public class MySavedProductsListFragment extends Fragment {
    View view;
    RecyclerView mySavedproRV;
    List<Product> listOfMySavedProduct = new LinkedList<>();
    MySavedProductListAdapter mySavedProAdapter;
    String myName;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_saved_products_list, container, false);
        mySavedproRV = view.findViewById(R.id.saved_products_list_recycle);
        myName = mAuth.getCurrentUser().getEmail();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mySavedproRV.setLayoutManager(layoutManager);
        mySavedProAdapter = new MySavedProductListAdapter();
        mySavedproRV.setAdapter(mySavedProAdapter);
        updateDisplay();
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDisplay() {
        model.instance.GetProductsIwant(listOfMySavedProduct, myName, catHash -> {
            mySavedProAdapter.setCategoryList(listOfMySavedProduct);
            mySavedProAdapter.notifyDataSetChanged();
        });
    }
}