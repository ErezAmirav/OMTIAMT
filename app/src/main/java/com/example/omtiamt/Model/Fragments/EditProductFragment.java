package com.example.omtiamt.Model.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omtiamt.R;
import com.squareup.picasso.Picasso;


public class EditProductFragment extends Fragment {
    View view;
    EditText editTextNameProduct;
    TextView textViewCategoryProduct;
    ImageView imageViewPictureProduct;
    EditText editTextAdressProduct;
    EditText editTextDetailsProduct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_product, container, false);
        String nameProudct = EditProductFragmentArgs.fromBundle(getArguments()).getNameProduct();
        String categoryProduct = EditProductFragmentArgs.fromBundle(getArguments()).getCategoryProduct();
        String pictureProduct = EditProductFragmentArgs.fromBundle(getArguments()).getPictureProduct();
        String adressProduct = EditProductFragmentArgs.fromBundle(getArguments()).getAdressProduct();
        String detailsProduct = EditProductFragmentArgs.fromBundle(getArguments()).getDetailsProduct();
        editTextNameProduct = view.findViewById(R.id.newproduct_name_id);
        imageViewPictureProduct = view.findViewById(R.id.image_preview);
        editTextAdressProduct = view.findViewById(R.id.adress_EditText);
        editTextDetailsProduct = view.findViewById(R.id.details_product_editText);
        textViewCategoryProduct = view.findViewById(R.id.textViewCategoryName);
        editTextNameProduct.setText(nameProudct);
        Picasso.with(this.getContext()).load(pictureProduct).resize(300, 300).into(imageViewPictureProduct);
        editTextAdressProduct.setText(adressProduct);
        editTextDetailsProduct.setText(detailsProduct);
        textViewCategoryProduct.setText(categoryProduct);
        return view;
    }
}