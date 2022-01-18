package com.example.omtiamt.Model.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    EditText editTextNameProduct;
    TextView textViewCategoryProduct;
    ImageView imageViewPictureProduct;
    EditText editTextAdressProduct;
    EditText editTextDetailsProduct;
    Button btn_Edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_product, container, false);
        String idProudct = EditProductFragmentArgs.fromBundle(getArguments()).getIdProduct();
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
        btn_Edit = view.findViewById(R.id.btn_edit_product);
        editTextNameProduct.setText(nameProudct);
        Picasso.with(this.getContext()).load(pictureProduct).resize(300, 300).into(imageViewPictureProduct);
        editTextAdressProduct.setText(adressProduct);
        editTextDetailsProduct.setText(detailsProduct);
        textViewCategoryProduct.setText(categoryProduct);

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMessageSureEdit(editTextNameProduct.getText().toString(),idProudct,imageViewPictureProduct.toString(),editTextAdressProduct.getText().toString(),editTextDetailsProduct.getText().toString());
            }
        });

        return view;
    }
    public void popupMessageSureEdit(String nameProudct,String idProudct,String pictureProduct, String adressProduct, String detailsProduct) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you want to edit this " + "" + nameProudct + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Delete item");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> EditMyProduct(nameProudct,idProudct,pictureProduct,adressProduct,detailsProduct));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void EditMyProduct(String nameProudct,String idProudct,String pictureProduct, String adressProduct, String detailsProduct) {
        model.instance.SetProduct(idProudct,nameProudct,adressProduct,detailsProduct,pictureProduct,()->{
            Toast.makeText(this.getContext(), "Product Edited", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_homePageFragment);
        });
    }
}