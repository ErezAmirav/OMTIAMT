package com.example.omtiamt.Model.Recylers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    OnItemClickListener listener;
    String myNameCategory;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCategoryMap(List<Product> ListOfProduct) {
        this.ListOfProduct = ListOfProduct;
    }

    private List<Product> ListOfProduct = new LinkedList<>();
    URL url;

    @NonNull
    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_readmore_product, parent, false);
        ProductListAdapter.ProductViewHolder holder = new ProductListAdapter.ProductViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        String userName = ListOfProduct.get(position).getUser();
        String name = ListOfProduct.get(position).getProductName();
        String pictureUrl = ListOfProduct.get(position).getProductPicture();
        String location = ListOfProduct.get(position).getLocation();
        String category = ListOfProduct.get(position).getCategory();
        Picasso.with(holder.picture.getContext()).load(pictureUrl).resize(300, 300).into(holder.picture);
        holder.namePTextView.setText(name);
        holder.userTextView.setText(userName);
        holder.adressTextView.setText(location);
        holder.categoryTextView.setText(category);
        holder.categoryTextView.setVisibility(View.GONE);
        if (myNameCategory.equals("View All")) {
            holder.categoryTextView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return ListOfProduct.size();
    }

    public void enableCategory(String nameCat) {
        myNameCategory = nameCat;

    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView namePTextView;
        public TextView adressTextView;
        public TextView userTextView;
        public ImageView picture;
        public TextView categoryTextView;

        public ProductViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            namePTextView = itemView.findViewById(R.id.title_id);
            adressTextView = itemView.findViewById(R.id.city_txt);
            userTextView = itemView.findViewById(R.id.email_view);
            picture = itemView.findViewById(R.id.product_image);
            categoryTextView = itemView.findViewById(R.id.textview_cat);
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
