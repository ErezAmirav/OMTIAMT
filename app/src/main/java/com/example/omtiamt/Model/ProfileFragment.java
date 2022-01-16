package com.example.omtiamt.Model;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omtiamt.Login;
import com.example.omtiamt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
                            startActivity(new Intent(ProfileFragment.this.getContext(), Login.class));
                        });
                        alert.setNegativeButton("No", (dialog, which) -> {
                            Toast.makeText(ProfileFragment.this.getContext(), "Aborted", Toast.LENGTH_LONG).show();
                        });
                        alert.create().show();
                        return true;

                    case R.id.item_logout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(ProfileFragment.this.getContext(), "Signing out, Goodbye!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ProfileFragment.this.getContext(), Login.class));
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

    }

    public void savedProducts(View view) {

    }
}