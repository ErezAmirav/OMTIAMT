package com.example.omtiamt.Model.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Model.Activity.BaseActivity;
import com.example.omtiamt.Model.Data.model;
import com.example.omtiamt.Model.Recylers.MyProductsListFragment;
import com.example.omtiamt.Model.Recylers.MySavedProductsListFragment;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {
    View view;
    TextView email;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userEmail;
    ImageButton settingsMenu;
    PopupMenu popupMenu;
    AlertDialog.Builder alert;
    Button myProductsBtn;
    Button savedProductsBtn;

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
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myProductsBtn = view.findViewById(R.id.profile_my_products_btn);
        savedProductsBtn = view.findViewById(R.id.profile_saved_products_btn);

        myProductsBtn.setOnClickListener(v ->
                myProducts(v));

        savedProductsBtn.setOnClickListener(v ->
                savedProducts(v));

        // Show Current User Email
        email = view.findViewById(R.id.profile_email_id);
        userEmail = " " + mAuth.getCurrentUser().getEmail();
        email.setText(userEmail);

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
                        alert.setMessage("Are You Sure ?");
                        alert.setPositiveButton("Yes", (dialog, which) -> {
                            currentUser.delete();
                            Toast.makeText(ProfileFragment.this.getContext(), "User Deleted", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);
                        });
                        alert.setNegativeButton("No", (dialog, which) -> {
                            Toast.makeText(ProfileFragment.this.getContext(), "Aborted", Toast.LENGTH_LONG).show();
                        });
                        alert.create().show();
                        return true;

                    case R.id.item_logout:
                        //FirebaseAuth.getInstance().signOut();
                        //currentUser = null;
                        model.instance.SignOut(()->{
                            Toast.makeText(ProfileFragment.this.getContext(), "Signing out, Goodbye!", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);
                        });

                        return true;

                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
        return view;
    }

    public void myProducts(View view) {

        Fragment myProductsList = new MyProductsListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.my_products_list_viewer, myProductsList).commit();
    }

    public void savedProducts(View view) {
        Fragment savedProductsList = new MySavedProductsListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.my_products_list_viewer, savedProductsList).commit();
    }
}