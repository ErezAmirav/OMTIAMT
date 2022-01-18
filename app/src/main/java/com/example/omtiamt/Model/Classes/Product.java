package com.example.omtiamt.Model.Classes;

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
    String location;
    String productPicture;
    String details;
    String userBuy;

    public Product(String id, String userName, String name, String picture, String details, String location) {
        this.id = id;
        this.user = userName;
        this.productName = name;
        this.productPicture = picture;
        this.details = details;
        this.location = location;
    }



    public Product(String id, String name, String category, String address, String details, String user, boolean isAvailable,String picture,String userBuy) {
        this.id = id;
        this.user = user;
        this.productName = name;
        this.category = category;
        this.isAvailable = isAvailable;
        this.location = address;
        this.productPicture = picture;
        this.details = details;
        this.userBuy = userBuy;
    }

    public String getUserBuy() {
        return userBuy;
    }

    public void setUserBuy(String userBuy) {
        this.userBuy = userBuy;
    }

    public Product() {

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setProductPicUrl (String url){
        this.productPicture = url;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("User",user);
        json.put("Category", category);
        json.put("Name", productName);
        json.put("Picture",productPicture);
        json.put("Location", location);
        json.put("Details", details);
        json.put("isAvailable", isAvailable);
        json.put("UserBuy", "nobody");
        return json;
    }
}


