package com.example.omtiamt.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omtiamt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class CategorylistAdapter extends RecyclerView.Adapter<CategorylistAdapter.CategoryViewHoleder> {

    private HashMap<String,String> categoryMap = getCatNameAndPictures();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    public CategorylistAdapter(HashMap<String, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    @NonNull
    @Override
    public CategoryViewHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Categoryview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_choose_category,parent,false);
        return new CategoryViewHoleder(Categoryview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHoleder holder, int position) {
        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            holder.nameTextView.setText(key);
            try {
                URL url = new URL(value.toString());
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url.toString()).getContent());
                holder.picture.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public int getItemCount() {
        return categoryMap.size();
    }

    public static class CategoryViewHoleder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView picture;
        public CategoryViewHoleder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.category_name_id);
            picture = itemView.findViewById(R.id.category_image_id);

        }
    }
    public HashMap<String,String> getCatNameAndPictures()
    {
        CollectionReference applicationsRef = rootRef.collection("Category");
        rootRef.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        DocumentReference applicationIdRef = applicationsRef.document(id);
                        String name = document.getString("Name");
                        String picture = document.getString("Picture");
                        categoryMap.put(name, picture);
                    }
                }
            }
        });
        return categoryMap;
    }
}
