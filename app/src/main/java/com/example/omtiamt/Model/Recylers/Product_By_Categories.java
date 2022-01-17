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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Product_By_Categories extends Fragment {
    View view;
    RecyclerView proRV;
    HashMap<String,String> catOrderByname = new HashMap<String,String>();
    List<Product> ListOfProduct = new LinkedList<>();
    ProductListAdapter proAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product__by__categories, container, false);
        proRV = view.findViewById(R.id.product_by_cat_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        proRV.setLayoutManager(layoutManager);
        model.instance.getProductsByCat(ListOfProduct,"Mobile", catHash -> {
            proAdapter.setCategoryMap(ListOfProduct);
            proAdapter.notifyDataSetChanged();
        });
        proAdapter = new ProductListAdapter();
        proRV.setAdapter(proAdapter);
        proRV.setHasFixedSize(true);
        return view;
    }
}