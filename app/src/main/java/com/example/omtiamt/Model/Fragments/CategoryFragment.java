package com.example.omtiamt.Model.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omtiamt.Model.Recylers.Product_By_Categories;
import com.example.omtiamt.R;


public class CategoryFragment extends Fragment {
    TextView TextViewname;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        String Name = CategoryFragmentArgs.fromBundle(getArguments()).getNameCategory();
        TextViewname = view.findViewById(R.id.category_name_id);
        TextViewname.setText(Name);
        Product_By_Categories fragment =(Product_By_Categories) getChildFragmentManager().findFragmentById(R.id.productByCat);
        fragment.setCategory(Name);


        return view;

    }
}