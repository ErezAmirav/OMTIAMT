package com.example.omtiamt.Model.Recylers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProductFragment extends Fragment {
    View view;
    TextView Textview_productName;
    TextView Textview_adress;
    TextView TextView_details;
    TextView TextView_user;
    ImageView ImageViewProduct;
    TextView TextViewYourProduct;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

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
        Textview_productName = view.findViewById(R.id.productname_id);
        TextViewYourProduct = view.findViewById(R.id.is_yours_id);
        TextViewYourProduct.setVisibility(View.GONE);
        Textview_adress = view.findViewById(R.id.product_city_id);
        TextView_details = view.findViewById(R.id.product_details_id);
        ImageViewProduct = view.findViewById(R.id.product_image_id);
        TextView_user = view.findViewById(R.id.product_username_id);
        model.instance.getProduct(id,product, pro -> {
            Textview_productName.setText(product.getProductName());
            Textview_adress.setText(product.getLocation());
            TextView_details.setText(product.getDetails());
            Picasso.with(this.getContext()).load(product.getProductPicture()).resize(300,300).into(ImageViewProduct);
            TextView_user.setText(product.getUser());
            String email = mAuth.getCurrentUser().getEmail();
            if(product.getUser().equals(email))
            {
                yourProductvoid();
            }
        });
        return view;
    }

    private void yourProductvoid() {
        TextViewYourProduct.setVisibility(View.VISIBLE);

    }
}