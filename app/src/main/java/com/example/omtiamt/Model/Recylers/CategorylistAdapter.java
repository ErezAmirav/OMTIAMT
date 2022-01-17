package com.example.omtiamt.Model.Recylers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omtiamt.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
interface OnItemClickListener{
    void onItemClick(View v,int position);
}
public class CategorylistAdapter extends RecyclerView.Adapter<CategorylistAdapter.CategoryViewHolder> {
    OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void setCategoryMap(HashMap<String, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    private HashMap<String,String> categoryMap = new LinkedHashMap<>();
    URL url;


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_choose_category,parent,false);
        CategoryViewHolder holder = new CategoryViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            String tmp = (new ArrayList<>(categoryMap.keySet())).get(position);
            String key = entry.getKey();
            String value = entry.getValue();
            if (tmp.equals(key)){
            Picasso.with(holder.picture.getContext()).load(value).into(holder.picture);
            holder.nameTextView.setText(key);
            }
        }
    }
    @Override
    public int getItemCount() {
        return categoryMap.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView picture;

        public CategoryViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.choose_category_name);
            picture = itemView.findViewById(R.id.choose_category_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v, pos);
                }
            });
        }
    }

}
