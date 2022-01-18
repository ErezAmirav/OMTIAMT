package com.example.omtiamt.Model.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.R;
import com.squareup.picasso.Picasso;


public class EditProductFragment extends Fragment {
    View view;
    EditText productName;
    TextView productCategory;
    ImageView productPicture;
    EditText productAddress;
    EditText productDetails;
    Button btn_Edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_product, container, false);
        String idProduct = EditProductFragmentArgs.fromBundle(getArguments()).getIdProduct();
        String nameProduct = EditProductFragmentArgs.fromBundle(getArguments()).getNameProduct();
        String categoryProduct = EditProductFragmentArgs.fromBundle(getArguments()).getCategoryProduct();
        String pictureProduct = EditProductFragmentArgs.fromBundle(getArguments()).getPictureProduct();
        String addressProduct = EditProductFragmentArgs.fromBundle(getArguments()).getAdressProduct();
        String detailsProduct = EditProductFragmentArgs.fromBundle(getArguments()).getDetailsProduct();
        productName = view.findViewById(R.id.newproduct_name_id);
        productPicture = view.findViewById(R.id.image_preview);
        productAddress = view.findViewById(R.id.adress_EditText);
        productDetails = view.findViewById(R.id.details_product_editText);
        productCategory = view.findViewById(R.id.textViewCategoryName);
        btn_Edit = view.findViewById(R.id.btn_edit_product);
        productName.setText(nameProduct);
        Picasso.with(this.getContext()).load(pictureProduct).resize(300, 300).into(productPicture);
        productAddress.setText(addressProduct);
        productDetails.setText(detailsProduct);
        productCategory.setText(categoryProduct);

        btn_Edit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(productName.getText().toString())){
                productName.setError("Name Field Cannot Be Empty");
                productName.requestFocus();
            } else if (TextUtils.isEmpty(productAddress.getText().toString())){
                productAddress.setError("Address Field Cannot Be Empty");
                productAddress.requestFocus();
            } else if (TextUtils.isEmpty(productDetails.getText().toString())){
                productDetails.setError("Details Field Cannot Be Empty");
                productDetails.requestFocus();
            } else
            popupMessageSureEdit(productName.getText().toString(),idProduct, productPicture.toString(), productAddress.getText().toString(), productDetails.getText().toString());
        });

        return view;
    }
    public void popupMessageSureEdit(String nameProduct,String idProduct,String pictureProduct, String addressProduct, String detailsProduct) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you want to edit this " + "" + nameProduct + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Delete item");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> EditMyProduct(nameProduct,idProduct,pictureProduct,addressProduct,detailsProduct));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void EditMyProduct(String nameProduct,String idProduct,String pictureProduct, String addressProduct, String detailsProduct) {
        model.instance.SetProduct(idProduct,nameProduct,addressProduct,detailsProduct,pictureProduct,()->{
            Toast.makeText(this.getContext(), "Product Edited", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_homePageFragment);
        });
    }
}