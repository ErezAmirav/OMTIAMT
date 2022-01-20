package com.example.omtiamt.Model.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.omtiamt.Model.Activity.BaseActivity;
import com.example.omtiamt.R;


public class homePageFragment extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BaseActivity.showTabBar();
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        return view;
    }
}
