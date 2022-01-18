package com.example.omtiamt.Model.Fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


public class EditProductFragment extends Fragment {
    View view;
    EditText name;
    TextView category;
    ImageView picImgView;
    EditText address;
    EditText details;
    Button saveBtn;
    Button uploadPhotoBtn;
    PopupMenu popUpPhoto;
    Bitmap imageBitmap;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_OPEN_GALLERY = 2;

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
        name = view.findViewById(R.id.newproduct_name_id);
        picImgView = view.findViewById(R.id.image_preview);
        address = view.findViewById(R.id.adress_EditText);
        details = view.findViewById(R.id.details_product_editText);
        category = view.findViewById(R.id.textViewCategoryName);
        saveBtn = view.findViewById(R.id.btn_edit_product);
        uploadPhotoBtn = view.findViewById(R.id.upload_photo_btn);

        uploadPhotoBtn.setOnClickListener(this::uploadPhoto);

        name.setText(nameProduct);
        Picasso.with(this.getContext()).load(pictureProduct).resize(300, 300).into(picImgView);
        address.setText(addressProduct);
        details.setText(detailsProduct);
        category.setText(categoryProduct);

        saveBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(name.getText().toString())) {
                name.setError("Name Field Cannot Be Empty");
                name.requestFocus();
            } else if (TextUtils.isEmpty(address.getText().toString())) {
                address.setError("Address Field Cannot Be Empty");
                address.requestFocus();
            } else if (TextUtils.isEmpty(details.getText().toString())) {
                details.setError("Details Field Cannot Be Empty");
                details.requestFocus();
            } else
                // TODO - Fix the save
                popupMessageSureEdit(name.getText().toString(), idProduct, picImgView.toString(), address.getText().toString(), details.getText().toString());
        });

        return view;
    }

    public void popupMessageSureEdit(String nameProduct, String idProduct, String pictureProduct, String addressProduct, String detailsProduct) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you want to edit this " + "" + nameProduct + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Delete item");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> EditMyProduct(nameProduct, idProduct, pictureProduct, addressProduct, detailsProduct));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void EditMyProduct(String nameProduct, String idProduct, String pictureProduct, String addressProduct, String detailsProduct) {
        model.instance.SetProduct(idProduct, nameProduct, addressProduct, detailsProduct, pictureProduct, () -> {
            Toast.makeText(this.getContext(), "Product Edited", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_homePageFragment);
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void uploadPhoto(View view) {
        popUpPhoto = new PopupMenu(EditProductFragment.this.getContext(), view);
        popUpPhoto.getMenuInflater().inflate(R.menu.upload_photo_popup, popUpPhoto.getMenu());
        popUpPhoto.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_open_camera:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    break;
                case R.id.item_gallery:
                    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                    openGalleryIntent.setType("image/*");
                    startActivityForResult(openGalleryIntent, REQUEST_OPEN_GALLERY);
                    break;

                default:
                    return false;
            }
            return false;
        });
        popUpPhoto.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                picImgView.setImageBitmap(imageBitmap);
            }
        }
        if (requestCode == REQUEST_OPEN_GALLERY) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imgStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imgStream);
                    picImgView.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(EditProductFragment.this.getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}