package com.example.omtiamt.Model;

import android.graphics.Picture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Product {
    final public static String COLLECTION_NAME = "Products";
    private FirebaseAuth mAuth;
    @PrimaryKey
    String id;
    String user;
    String productName;
    String category;
    Boolean isAvailable;
    String loaction;
    String productPicture;
    String Details;

    public Product(String id, String name, String category, String address, String details, String user, boolean isAvailable,String picture) {
        this.id = id;
        this.user = user;
        this.productName = name;
        this.category = category;
        this.isAvailable = isAvailable;
        this.loaction = address;
        this.productPicture = picture;
        this.Details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }


    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("User",user);
        json.put("Category", category);
        json.put("name", productName);
        json.put("Picture", productPicture);
        json.put("Location", loaction);
        json.put("Details", Details);
        json.put("isAvailable", isAvailable);
        return json;
    }
    /*public static Product create(Map<String, Object> json) {
        String id = (String) json.get("id");
        Users user = (Users) json.get("User");
        Categories category = (Categories) json.get("Category");
        String productname = (String) json.get("name");
        String email = (String) json.get("email");
        String location = (String) json.get("Location");
        String picture = (String) json.get("Picture");
        String details = (String) json.get("Details");
        Product product = new Product(id,user,productname,category,true,location,picture,details);
        return  product;
    }
    */
}


