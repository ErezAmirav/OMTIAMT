package com.example.omtiamt.Model;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;


public class NewProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner catList;
    EditText namePro;
    EditText addressPro;
    EditText detailsPro;
    Button uploadPhotoBtn;
    Button publishBtn;
    FirebaseStorage storage;
    StorageReference storageReference;
    PopupMenu popUpPhoto;
    ImageView prevImage;
    Bitmap imageBitmap;
    private FirebaseAuth mAuth;
    String emailUser;
    FirebaseUser currentUser;

    private static final int REQUEST_CAMERA = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_OPEN_GALLERY = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_product, container, false);
        catList = view.findViewById(R.id.choose_category_id);
        uploadPhotoBtn = view.findViewById(R.id.upload_photo_btn);
        publishBtn = view.findViewById(R.id.publish_product_btn);
        prevImage = view.findViewById(R.id.image_preview);
        namePro = view.findViewById(R.id.newproduct_name_id);
        addressPro = view.findViewById(R.id.adress_EditText);
        detailsPro = view.findViewById(R.id.details_product_editText);
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catList.setAdapter(adapter);
        catList.setOnItemSelectedListener(this);
        publishBtn.setOnClickListener(v -> popupMessageSure(namePro.getText().toString()));
        uploadPhotoBtn.setOnClickListener(this::uploadPhoto);
        return view;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cateChoose = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void publishProduct() {
        String id = "";
        String name = namePro.getText().toString();
        String category = catList.getSelectedItem().toString();
        String address = addressPro.getText().toString();
        String details = detailsPro.getText().toString();
        String userEmail = mAuth.getCurrentUser().getEmail();

        Product product = new Product(id, name, category, address, details, userEmail, true, null);
        if (imageBitmap != null) {
            model.instance.saveImage(imageBitmap, name + ".jpg", url -> {
                product.setProductPicUrl(url);
                model.instance.addProduct(product, this::restartPage);
            });
        } else {
            model.instance.addProduct(product, this::restartPage);
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void uploadPhoto(View view) {
        popUpPhoto = new PopupMenu(NewProductFragment.this.getContext(), view);
        popUpPhoto.getMenuInflater().inflate(R.menu.upload_photo_popup, popUpPhoto.getMenu());
        popUpPhoto.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_open_camera:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                case R.id.item_gallery:
                    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                    openGalleryIntent.setType("image/*");
                    startActivityForResult(openGalleryIntent, REQUEST_OPEN_GALLERY);

                default:
                    return false;
            }
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
                prevImage.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == REQUEST_OPEN_GALLERY) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imgStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imgStream);
                    prevImage.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(NewProductFragment.this.getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void restartPage() {
        popupMessageEnd();
        namePro.setText("");
        addressPro.setText("");
        detailsPro.setText("");
        prevImage.setImageBitmap(null);
    }

    public void popupMessageEnd() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Product added!");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("Success");
        alertDialogBuilder.setNegativeButton("Ok", (dialogInterface, i) -> { });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void popupMessageSure(String name) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Are you sure you want to add this " + "" + name + "?");
        alertDialogBuilder.setIcon(R.drawable.additem);
        alertDialogBuilder.setTitle("New Product");
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> publishProduct());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}