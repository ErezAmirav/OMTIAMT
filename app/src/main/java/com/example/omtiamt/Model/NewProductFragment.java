package com.example.omtiamt.Model;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.omtiamt.R;

import java.util.LinkedList;
import java.util.List;


public class NewProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner catList;
    TextView TextViewcatChoose;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_product, container, false);
        catList = view.findViewById(R.id.choose_category_id);
        TextViewcatChoose = view.findViewById(R.id.Textview_catChoose);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catList.setAdapter(adapter);
        catList.setOnItemSelectedListener(this);
        return view;
    }

    public void getCategoryName(){
        List<String> list = new LinkedList<String>();
        list = model.instance.getAllCategoriesName();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cateChoose = parent.getItemAtPosition(position).toString();
        TextViewcatChoose.setText(cateChoose);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}