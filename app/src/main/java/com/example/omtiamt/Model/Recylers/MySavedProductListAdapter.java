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
import java.util.LinkedList;
import java.util.List;

public class MySavedProductListAdapter extends RecyclerView.Adapter<MySavedProductListAdapter.MySavedProductViewHolder> {
    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<Product> ListOfProduct) {
        this.listOfMySavedProduct = ListOfProduct;
    }

    private List<Product> listOfMySavedProduct = new LinkedList<>();

    @NonNull
    @Override
    public MySavedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_readmore_product, parent, false);
        MySavedProductViewHolder holder = new MySavedProductViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySavedProductViewHolder holder, int position) {
        String userName = listOfMySavedProduct.get(position).getUser();
        String name = listOfMySavedProduct.get(position).getProductName();
        String pictureUrl = listOfMySavedProduct.get(position).getProductPicture();
        String location = listOfMySavedProduct.get(position).getLocation();
        String category = listOfMySavedProduct.get(position).getCategory();
        Picasso.with(holder.picture.getContext()).load(pictureUrl).resize(300, 300).into(holder.picture);
        holder.namePTextView.setText(name);
        holder.userTextView.setText(userName);
        holder.addressTextView.setText(location);
        holder.categoryTextView.setText(category);
    }

    @Override
    public int getItemCount() {
        return listOfMySavedProduct.size();
    }

    public static class MySavedProductViewHolder extends RecyclerView.ViewHolder {
        public TextView namePTextView;
        public TextView addressTextView;
        public TextView userTextView;
        public ImageView picture;
        public TextView categoryTextView;

        public MySavedProductViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            namePTextView = itemView.findViewById(R.id.title_id);
            addressTextView = itemView.findViewById(R.id.city_txt);
            userTextView = itemView.findViewById(R.id.email_view);
            picture = itemView.findViewById(R.id.product_image);
            categoryTextView = itemView.findViewById(R.id.textview_cat);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                listener.onItemClick(v, pos);
            });
        }
    }
}
