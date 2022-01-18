package com.example.omtiamt.Model.Recylers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.Model.Fragments.CategoryFragmentDirections;
import com.example.omtiamt.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ProductByCategories extends Fragment {
    View view;
    RecyclerView proRV;
    HashMap<String, String> catOrderByname = new HashMap<String, String>();
    List<Product> listOfProduct = new LinkedList<>();
    ProductListAdapter proAdapter;
    String nameCat;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product__by__categories, container, false);
        proRV = view.findViewById(R.id.product_by_cat_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        proRV.setLayoutManager(layoutManager);
        proAdapter = new ProductListAdapter();
        proRV.setAdapter(proAdapter);
        if (nameCat != null)
            updateDisplay();

        proAdapter.setOnItemClickListener((v, position) -> {
            String tmp = listOfProduct.get(position).getId();
            Navigation.findNavController(view).navigate(CategoryFragmentDirections.actionCategoryFragmentToProductFragment(tmp));
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDisplay() {
        model.instance.getProductsByCat(listOfProduct, nameCat, catHash -> {
            proAdapter.setCategoryMap(listOfProduct);
            proAdapter.notifyDataSetChanged();
            proAdapter.enableCategory(nameCat);
        });
    }

    public void setCategory(String name) {
        nameCat = name;
        if (view != null) {
            updateDisplay();
        }
    }
}