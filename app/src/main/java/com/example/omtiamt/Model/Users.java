package com.example.omtiamt.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Users {
    final public static String COLLECTION_NAME = "Users";
    @PrimaryKey
    String id;
    String name;
    String email;
    String password;
    String phone;
    List<Product> myProduct;
    List<Product> savedProduct;
    int idCounter = 0;




    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public List<Product> getMyProduct() {
        return myProduct;
    }

    public List<Product> getSavedProduct() {
        return savedProduct;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMyProduct(List<Product> myProduct) {
        this.myProduct = myProduct;
    }

    public void setSavedProduct(List<Product> savedProduct) {
        this.savedProduct = savedProduct;
    }

    public Users(String id, String name, String email, String password, String phone, List<Product> myProduct, List<Product> savedProduct) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.myProduct = myProduct;
        this.savedProduct = savedProduct;
    }

    public Users(String name, String password,String email) {
        this.id = String.valueOf(idCounter);
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = "";
        this.myProduct = null;
        this.savedProduct = null;
        idCounter++;
    }
    public Users(String id, String name, String password,String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = "";
        this.myProduct = null;
        this.savedProduct = null;
        idCounter++;
    }

        public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("name", name);
        json.put("password", password);
        json.put("email", email);
        return json;
    }
    public static Users create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String name = (String) json.get("name");
        String password = (String) json.get("password");
        String email = (String) json.get("email");

        Users user = new Users(id, name, email, password);
        return  user;
    }
}
