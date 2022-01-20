package com.example.omtiamt.Model.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Model.Data.model;
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
    Button dontNeedIt;
    String email;
    Button backBtn;
    Button iTookitBtn;

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
        iTookitBtn = view.findViewById(R.id.btn_took_it);
        iWantItBtn = view.findViewById(R.id.btn_i_want_it);
        deleteBtn = view.findViewById(R.id.btn_delete_product);
        dontNeedIt = view.findViewById(R.id.btn_dont_need);
        backBtn = view.findViewById(R.id.product_back_btn);
        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        dontNeedIt.setVisibility(View.GONE);
        iTookitBtn.setVisibility(View.GONE);
        NotYourProduct();
        addressTV = view.findViewById(R.id.product_city_id);
        detailsTV = view.findViewById(R.id.product_details_id);
        productImgV = view.findViewById(R.id.product_image_id);
        userTV = view.findViewById(R.id.product_username_id);
        model.instance.getProduct(id, product, pro -> {
            productNameTV.setText(product.getProductName());
            addressTV.setText(product.getLocation());
            detailsTV.setText(product.getDetails());
            Picasso.with(this.getContext()).load(product.getProductPicture()).resize(300, 300).into(productImgV);
            userTV.setText(product.getUser());
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userBuy = product.getUserBuy();
            email = currentUser.getEmail();
            if (product.getUser().equals(email)) {
                ItsYourProduct();
            }
            if(product.getUserBuy().equals(email))
            {
                dontNeedIt.setVisibility(View.VISIBLE);
                iTookitBtn.setVisibility(View.VISIBLE);
                iWantItBtn.setVisibility(View.GONE);
            }
        });
        editBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(ProductFragmentDirections.actionProductFragmentToEditProductFragment
                (product)));

        iWantItBtn.setOnClickListener(v ->
                popupMessageSureTake(product.getProductName(),id,email));

        deleteBtn.setOnClickListener(v ->
                popupMessageSureDelete(product.getProductName(),id));

        dontNeedIt.setOnClickListener(v ->
                popupMessageSureDontNeed(id,product.getProductName()));
        iTookitBtn.setOnClickListener(v ->
                popupMessageSureUwasTookit(id,product.getProductName()));
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
    public void popupMessageSureUwasTookit(String id,String name) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you was took this " + "" + name + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Congratulations!");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> ITookit(id));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ITookit(String id) {
        model.instance.ITookit(id,() -> {
            Toast.makeText(this.getContext(), "Hope you Enjoy!", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_productFragment_to_homePageFragment);
        });
    }

    public void popupMessageSureDelete(String name,String id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you want to delete this " + "" + name + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Delete item");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> DeleteMyProduct(id));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void popupMessageSureTake(String name,String id,String email) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you want to take this " + "" + name + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Congratulations!");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> AddBuytoProduct(id,email));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void DeleteMyProduct(String id) {
        model.instance.DeleteProduct(id,()->{
            Toast.makeText(this.getContext(), "Product deleted", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_productFragment_to_homePageFragment);
        });
    }

    private void AddBuytoProduct(String id,String email) {
        model.instance.setTakenProduct(id, email, () -> {
            Toast.makeText(this.getContext(), "Hope you enjoy", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_productFragment_to_homePageFragment);
        });
    }
        public void popupMessageSureDontNeed(String id,String name) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
            alertDialogBuilder.setMessage("Are you sure you don't need this " + "" + name + "?");
            alertDialogBuilder.setIcon(R.drawable.additem);
            alertDialogBuilder.setTitle("Delete item");
            alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> dontNeedit(id));
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    private void dontNeedit(String id) {
        model.instance.DontNeedit(id,() -> {
            Toast.makeText(this.getContext(), "Feel free to look for new products", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_productFragment_to_homePageFragment);
        });
    }
}