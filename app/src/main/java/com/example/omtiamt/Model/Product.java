package com.example.omtiamt.Model;

import android.graphics.Picture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Product {
    final public static String COLLECTION_NAME = "Product";
    @PrimaryKey
    String id;
    Users user;
    String productName;
    Categories category;
    Boolean isAvailable;
    String loaction;
    String productPicture;
    String Details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
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

    public Product(String id, Users user, String productName, Categories category, Boolean isAvailable, String loaction, String productPicture, String details) {
        this.id = id;
        this.user = user;
        this.productName = productName;
        this.category = category;
        this.isAvailable = isAvailable;
        this.loaction = loaction;
        this.productPicture = productPicture;
        Details = details;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("User", user);
        json.put("Category", category);
        json.put("name", productName);
        json.put("Picture", productPicture);
        json.put("Location", loaction);
        json.put("Details", Details);
        return json;
    }
    public static Product create(Map<String, Object> json) {
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
}
