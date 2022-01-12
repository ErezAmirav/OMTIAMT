package com.example.omtiamt.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omtiamt.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CategorylistAdapter extends RecyclerView.Adapter<CategorylistAdapter.CategoryViewHolder> {

    private HashMap<String,String> categoryMap;
    URL url;
    public CategorylistAdapter(HashMap<String, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View CategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_choose_category,parent,false);
        return new CategoryViewHolder(CategoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        //categoryMap = model.instance.getCatNameAndPictures();

        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            String keyName = entry.getKey();
            String valuePic = entry.getValue();
            holder.nameTextView.setText(keyName);
            model.instance.urlToImg(valuePic, holder.picture);

        }
    }
    @Override
    public int getItemCount() {
        return categoryMap.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView picture;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.category_name_id);
            picture = itemView.findViewById(R.id.category_image_id);

        }
    }

}
