package com.example.omtiamt.Model;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.omtiamt.R;

import java.util.LinkedList;
import java.util.List;


public class NewProductFragment extends Fragment {
    View view;
    Spinner catList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_product, container, false);
        catList = view.findViewById(R.id.choose_category_id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewProductFragment.this.getContext()
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        catList.setAdapter(adapter);
        return view;
    }

    public void getCategoryName(){
        List<String> list = new LinkedList<String>();
        list = model.instance.getAllCategoriesName();

    }
}