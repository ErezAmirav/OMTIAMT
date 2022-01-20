package com.example.omtiamt.Model.Classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {
    final public static String COLLECTION_NAME = "Users";
    String id;
    String email;
    String imageUrl;

    public Users(String id, String email, String image) {
        this.id = id;
        this.email = email;
        this.imageUrl = image;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("email", "");
        json.put("imageUrl", "");
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

}
