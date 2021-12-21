package com.example.omtiamt.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Users {
    @PrimaryKey
    String id;
    String name;
    String email;
    String password;
    String phone;
    List<Product> myProduct;
    List<Product> savedProduct;

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
        this.id = "1";
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = "";
        this.myProduct = null;
        this.savedProduct = null;
    }
}
