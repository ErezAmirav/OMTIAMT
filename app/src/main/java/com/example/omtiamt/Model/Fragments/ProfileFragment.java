package com.example.omtiamt.Model.Fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.omtiamt.Model.Activity.BaseActivity;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.Model.Recylers.MyProductsListFragment;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


public class ProfileFragment extends Fragment {
    View view;
    TextView email;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userEmail;
    String userEmail2;
    ImageButton settingsMenu;
    PopupMenu popupMenu;
    AlertDialog.Builder alert;
    ImageView profilePicture;
    View fragmentMyProduct;
    ProgressBar progressBar;
    AlertDialog.Builder newPictureAlert;
    AlertDialog.Builder newUserName;
    TextView profileName;
    String imageUrl;
    Bitmap imageBitmap;
    String nameUser;
    private String myText;



    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_OPEN_GALLERY = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BaseActivity.showTabBar();
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileName = view.findViewById(R.id.profile_name_id);
        progressBar = view.findViewById(R.id.product_by_category_pb);
        progressBar.setVisibility(View.GONE);
        profilePicture = view.findViewById(R.id.profile_picture_iv);

        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Show Current User Email
        email = view.findViewById(R.id.profile_email_id);
        userEmail = " " + mAuth.getCurrentUser().getEmail();
        userEmail2 = mAuth.getCurrentUser().getEmail();
        email.setText(userEmail);

        MyProductsListFragment fragment = (MyProductsListFragment) getChildFragmentManager().findFragmentById(R.id.productByMe);
        fragment.SetMyName(userEmail2);
        fragmentMyProduct = view.findViewById(R.id.productByMe);

        model.instance.GetNameCurrentUser(userEmail2,(listener)->{
            nameUser = listener;
            if (nameUser != "")
            profileName.setText(nameUser);
        });
        model.instance.GetPictureCurrentUser(userEmail2,(listener)->{
            imageUrl = listener;
            if(imageUrl != "")
            Picasso.with(this.getContext()).load(imageUrl).resize(300, 300).into(profilePicture);

        });
        //profileName.setText(nameUser);

        // Settings Popup Menu
        settingsMenu = view.findViewById(R.id.profile_settings_id);
        settingsMenu.setOnClickListener(v -> {
            popupMenu = new PopupMenu(ProfileFragment.this.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.settings_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        // Alert Dialog to Confirm
                        alert = new AlertDialog.Builder(ProfileFragment.this.getContext());
                        alert.setTitle("Delete User");
                        alert.setMessage("Are You Sure ? all your products will deleted");
                        alert.setPositiveButton("Yes", (dialog, which) -> {
                            model.instance.DeleteUser(() -> {
                                progressBar.setVisibility(View.VISIBLE);
                                currentUser.delete();
                                Toast.makeText(ProfileFragment.this.getContext(), "User Deleted", Toast.LENGTH_LONG).show();
                                BaseActivity.hideTabBar();
                                progressBar.setVisibility(View.GONE);
                                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_registerFragment);
                            });
                        });
                        alert.setNegativeButton("No", (dialog, which) ->
                                Toast.makeText(ProfileFragment.this.getContext(), "Aborted", Toast.LENGTH_LONG).show());
                        alert.create().show();
                        return true;

                    case R.id.item_logout: {
                        progressBar.setVisibility(View.VISIBLE);
                        model.instance.SignOut();
                        Toast.makeText(ProfileFragment.this.getContext(), "Signing out, Goodbye!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getContext(), BaseActivity.class);
                        startActivity(intent);
                    }
                    return true;
                    case R.id.item_change_profile_picture:
                        newPictureAlert = new AlertDialog.Builder(ProfileFragment.this.getContext());
                        newPictureAlert.setTitle("Change Profile Picture");
                        newPictureAlert.setMessage("Take Picture / Open Gallery");

                        newPictureAlert.setPositiveButton("Open Camera", (dialog, which) -> {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        });

                        newPictureAlert.setNegativeButton("Open Gallery", (dialog, which) -> {
                            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                            openGalleryIntent.setType("image/*");
                            startActivityForResult(openGalleryIntent, REQUEST_OPEN_GALLERY);
                        });
                        newPictureAlert.create().show();
                        return true;

                    case R.id.item_my_saved_products:
                        Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToProductIWantFragment2(userEmail2));
                        return true;

                    case R.id.item_change_profile_name:
                        newUserName = new AlertDialog.Builder(ProfileFragment.this.getContext());
                        newUserName.setTitle("Full Details");

                        final EditText fullDetails = new EditText(ProfileFragment.this.getContext());
                        fullDetails.setInputType(InputType.TYPE_CLASS_TEXT);
                        newUserName.setView(fullDetails);

                        newUserName.setPositiveButton("Confirm", (dialog, which) -> {
                            myText = fullDetails.getText().toString();
                            model.instance.SetNameCurrentUser(userEmail2,myText, (nameUser)->{
                                profileName.setText(nameUser);
                            });
                        });
                        newUserName.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                        newUserName.show();
                        return true;

                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                progressBar.setVisibility(View.VISIBLE);
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                model.instance.saveImage(imageBitmap,  nameUser+ ".jpg", url -> {
                    model.instance.SetPictureCurrentUser(userEmail2,url,()-> {
                        Picasso.with(this.getContext()).load(url).resize(300, 300).into(profilePicture);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileFragment.this.getContext(), "Profile Picture Changed", Toast.LENGTH_LONG).show();
                    });
                });

            }
        }
        if (requestCode == REQUEST_OPEN_GALLERY) {
            if (resultCode == RESULT_OK) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imgStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imgStream);
                    model.instance.saveImage(imageBitmap,  nameUser+ ".jpg", url -> {
                        model.instance.SetPictureCurrentUser(userEmail2,url,()-> {
                            progressBar.setVisibility(View.GONE);
                            Picasso.with(this.getContext()).load(url).resize(300, 300).into(profilePicture);
                            Toast.makeText(ProfileFragment.this.getContext(), "Profile Picture Changed", Toast.LENGTH_LONG).show();
                        });
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileFragment.this.getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}