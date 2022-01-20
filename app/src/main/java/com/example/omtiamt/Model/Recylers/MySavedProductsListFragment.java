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
import com.example.omtiamt.R;
import java.util.LinkedList;
import java.util.List;

public class MySavedProductsListFragment extends Fragment {
    View view;
    RecyclerView mySavedProRV;
    List<Product> listOfMySavedProduct = new LinkedList<>();
    MySavedProductListAdapter mySavedProAdapter;
    String myName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_saved_products_list, container, false);
        mySavedProRV = view.findViewById(R.id.saved_products_list_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mySavedProRV.setLayoutManager(layoutManager);
        mySavedProAdapter = new MySavedProductListAdapter();
        mySavedProRV.setAdapter(mySavedProAdapter);
        if (myName != null)
            updateDisplay();
        mySavedProAdapter.setOnItemClickListener((v, position) -> {
            String tmp = listOfMySavedProduct.get(position).getId();
            Navigation.findNavController(view).navigate(ProductIWantFragmentDirections.actionProductIWantFragmentToProductFragment(tmp));
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDisplay() {
        model.instance.GetProductsIWant(listOfMySavedProduct, myName, catHash -> {
            mySavedProAdapter.setList(listOfMySavedProduct);
            mySavedProAdapter.notifyDataSetChanged();
        });
    }

    public void SetMyName(String userEmail2) {
        myName = userEmail2;
        if (view != null) {
            updateDisplay();
        }
    }
}