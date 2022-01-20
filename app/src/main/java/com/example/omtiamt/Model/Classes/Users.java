package com.example.omtiamt.Model.Classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {
    final public static String COLLECTION_NAME = "Users";
    String id;
    String email;
    String imageUrl;
    String name;

    public Users(String id, String email, String image,String name) {
        this.id = id;
        this.email = email;
        this.imageUrl = image;
        this.name = name;

    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("email", "");
        json.put("imageUrl", "");
        json.put("name", "");
        return json;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String image) {
        imageUrl = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
