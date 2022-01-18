package com.example.omtiamt.Model.Recylers;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omtiamt.Model.Data.Model;
import com.example.omtiamt.Model.Fragments.homePageFragmentDirections;
import com.example.omtiamt.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_List_Fragment extends Fragment {
    View view;
    RecyclerView catRV;
    HashMap<String,String> myCatHash = new HashMap<String,String>();
    CategoryListAdapter catAdapter;

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

        Model.instance.getCatNameAndPictures(myCatHash, catHash -> {
         catAdapter.setCategoryMap(catHash);
         catAdapter.notifyDataSetChanged();
        });

        catAdapter = new CategoryListAdapter();
        catRV.setAdapter(catAdapter);

        catAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String tmp = (new ArrayList<>(myCatHash.keySet())).get(position);
                Navigation.findNavController(view).navigate(homePageFragmentDirections.actionHomePageFragmentToCategoryFragment(tmp));
            }
        });
        return view;
    }
}