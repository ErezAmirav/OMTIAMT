package com.example.omtiamt.Model;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.omtiamt.Login;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class homePageFragment extends Fragment {
    List<String> catData;
    View view;
    FirebaseAuth mAuth;
    List<String> list;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();



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

        mAuth = FirebaseAuth.getInstance();


    }

    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(homePageFragment.this.getContext(), Login.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        List<String> catList = model.instance.getCategoryNames();


        Fragment categorylist = new Category_List_Fragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.category_fragment_viewer, categorylist).commit();

        return view;


    }

}
