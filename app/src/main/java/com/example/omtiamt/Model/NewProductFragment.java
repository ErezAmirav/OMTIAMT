package com.example.omtiamt.Model;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.omtiamt.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedList;
import java.util.List;


public class NewProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner catList;
    TextView TextViewcatChoose;
    Button uploadPhotoBtn;
    Button publishBtn;
    FirebaseStorage storage;
    StorageReference storageReference;
    PopupMenu popUpPhoto;
    ImageView prevImage;
    Bitmap imageBitmap;

    private final int PICK_IMAGE_REQUEST = 22;
    static final int REQUEST_IMAGE_CAPTURE = 1;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catList.setAdapter(adapter);
        catList.setOnItemSelectedListener(this);

        uploadPhotoBtn.setOnClickListener(this::uploadPhoto);

        return view;
    }

    public void getCategoryName(){
        List<String> list = new LinkedList<String>();
        list = model.instance.getAllCategoriesName();

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cateChoose = parent.getItemAtPosition(position).toString();
       // TextViewcatChoose.setText(cateChoose);
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void publishProduct(View view) {
    }

    @SuppressLint("NonConstantResourceId")
    public void uploadPhoto(View view) {
        popUpPhoto = new PopupMenu(NewProductFragment.this.getContext(), view);
        popUpPhoto.getMenuInflater().inflate(R.menu.upload_photo_popup, popUpPhoto.getMenu());
        popUpPhoto.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.item_open_camera:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
                case R.id.item_gallery:


                default:
                    return false;
            }
        });
        popUpPhoto.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            if (resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                prevImage.setImageBitmap(imageBitmap);
            }
        }
    }
}