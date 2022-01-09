package com.example.omtiamt.Model;

import android.graphics.Picture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Categories {
    final public static String COLLECTION_NAME = "Category";
    @PrimaryKey
    String id;
    String categoryName;
    String categoryPicture;
    List<Product> products;

    public Categories() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPicture() {
        return categoryPicture;
    }

    public void setCategoryPicture(String categoryPicture) {
        this.categoryPicture = categoryPicture;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Categories(String id, String categoryName, String categoryPicture, List<Product> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryPicture = categoryPicture;
        this.products = products;
    }
    public Categories(String categoryName, String categoryPicture) {
        this.categoryName = categoryName;
        this.categoryPicture = categoryPicture;
    }
}
