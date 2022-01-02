package com.example.omtiamt.Model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.omtiamt.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class homePageFragment extends Fragment {
    List<String> catData;
    View view;
    public homePageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static homePageFragment newInstance(String param1, String param2) {
        homePageFragment fragment = new homePageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        return inflater.inflate(R.layout.fragment_home_page, container, false);


    }

    }
