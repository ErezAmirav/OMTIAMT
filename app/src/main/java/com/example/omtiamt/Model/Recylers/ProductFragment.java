package com.example.omtiamt.Model.Recylers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omtiamt.Model.Data.Model;
import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProductFragment extends Fragment {
    View view;
    TextView productNameTV;
    TextView addressTV;
    TextView detailsTV;
    TextView userTV;
    ImageView productImgV;
    TextView viewYourProductTV;
    FirebaseUser currentUser;
    Button editBtn;
    Button deleteBtn;
    Button iWantItBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        String id = ProductFragmentArgs.fromBundle(getArguments()).getProductId();
        Product product = new Product();
        productNameTV = view.findViewById(R.id.productname_id);
        viewYourProductTV = view.findViewById(R.id.is_yours_id);
        editBtn = view.findViewById(R.id.btn_edit_product);
        iWantItBtn = view.findViewById(R.id.btn_i_want_it);
        deleteBtn = view.findViewById(R.id.btn_delete_product);
        NotYourProduct();
        addressTV = view.findViewById(R.id.product_city_id);
        detailsTV = view.findViewById(R.id.product_details_id);
        productImgV = view.findViewById(R.id.product_image_id);
        userTV = view.findViewById(R.id.product_username_id);
        Model.instance.getProduct(id, product, pro -> {
            productNameTV.setText(product.getProductName());
            addressTV.setText(product.getLocation());
            detailsTV.setText(product.getDetails());
            Picasso.with(this.getContext()).load(product.getProductPicture()).resize(300, 300).into(productImgV);
            userTV.setText(product.getUser());
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String email = currentUser.getEmail();
            if (product.getUser().equals(email)) {
                ItsYourProduct();
            }
        });
        return view;
    }

    private void ItsYourProduct() {
        viewYourProductTV.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.VISIBLE);
        iWantItBtn.setVisibility(View.GONE);
    }

    private void NotYourProduct() {
        editBtn.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.GONE);
        viewYourProductTV.setVisibility(View.GONE);
    }
}