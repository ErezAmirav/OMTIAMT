package com.example.omtiamt.Model.Recylers;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.Model.Fragments.ProductIWantFragmentDirections;
import com.example.omtiamt.Model.Fragments.ProfileFragmentDirections;
import com.example.omtiamt.R;


import java.util.LinkedList;
import java.util.List;


public class MyProductsListFragment extends Fragment {
    View view;
    RecyclerView myProRV;
    List<Product> listOfMyProduct = new LinkedList<>();
    MyProductListAdapter myProAdapter;
    String myName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_products_list, container, false);
        myProRV = view.findViewById(R.id.my_products_list_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        myProRV.setLayoutManager(layoutManager);
        myProAdapter = new MyProductListAdapter();
        myProRV.setAdapter(myProAdapter);
        if (myName != null)
            updateDisplay();
        myProAdapter.setOnItemClickListener((v, position) -> {
            String tmp = listOfMyProduct.get(position).getId();
            String idan = "";
            Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToProductFragment2(tmp));
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDisplay() {
        model.instance.GetProductsByMe(listOfMyProduct, myName, catHash -> {
            myProAdapter.setCategoryList(listOfMyProduct);
            myProAdapter.notifyDataSetChanged();
        });
    }

    public void SetmyName(String userEmail) {
        myName = userEmail;
        if (view != null) {
            updateDisplay();
        }
    }
}