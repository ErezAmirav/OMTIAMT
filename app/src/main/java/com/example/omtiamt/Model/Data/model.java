package com.example.omtiamt.Model.Data;

import android.graphics.Bitmap;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Classes.Users;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;

public class model {
    public static final model instance = new model();
    ModelFirebase modelFirebase = new ModelFirebase();

    private model() {
    }

    // Delete User and all it's data
    public interface deleteUser {
        void onComplete();
    }

    public void DeleteUser(deleteUser listener) {
        modelFirebase.DeleteUser(listener);
    }

    // Add new Product
    public interface addProductListener {
        void onComplete();
    }

    public void addProduct(Product product, addProductListener listener) {
        modelFirebase.addProduct(product, listener);
    }

    // Add new User
    public interface addUserListener {
        void onComplete();
    }

    public void AddUser(Users user, addUserListener listener) {
        modelFirebase.AddUser(user,listener);
    }

    // Get Product by id
    public interface getProductListener {
        void onComplete(Product product);
    }

    public void getProduct(String id, Product product, getProductListener listener) {
        modelFirebase.getProduct(id, product, listener);
    }

    // Register New User
    public void registerNewUser(String email, String password) {
        modelFirebase.registerNewUser(email, password);
    }

    // Check if the email exists
    public boolean checkEmail(String email) {
        return modelFirebase.checkEmail(email);
    }

    // Get all the name and pictures of Category by id - to HashMap
    public interface getCatNameAndPictures {
        void onComplete(HashMap<String, String> catHash);
    }

    public void getCatNameAndPictures(HashMap<String, String> catHash, getCatNameAndPictures listener) {
        modelFirebase.getCatNameAndPictures(catHash, listener);
    }

    // Save Image to Firebase
    public interface saveImageListener {
        void onComplete(String url);
    }

    public void saveImage(Bitmap imgBitmap, String imgName, saveImageListener listener) {
        modelFirebase.saveImg(imgBitmap, imgName, listener);
    }

    // Delete Product by id
    public interface deleteProduct {
        void onComplete();
    }

    public void DeleteProduct(String id, deleteProduct listener) {
        modelFirebase.DeleteProduct(id, listener);
    }

    // Set Product
    public interface setProduct {
        void onComplete();
    }

    public void SetProduct(Product product, setProduct listener) {
        modelFirebase.SetProduct(product, listener);
    }

    // SignOut
    public void SignOut() {
        modelFirebase.SignOut();
    }

    // Set the product taker
    public interface setTakenProduct {
        void onComplete();
    }

    public void setTakenProduct(String idProduct, String nameTaker, setTakenProduct listener) {
        modelFirebase.SetTakenProduct(idProduct, nameTaker, listener);
    }

    // Cancel saved product and return it to available list
    public interface dontNeedIt {
        void onComplete();
    }

    public void DontNeedIt(String idProduct, dontNeedIt listener) {
        modelFirebase.DontNeedIt(idProduct, listener);
    }

    // The product not available anymore
    public interface iWasTookIt {
        void onComplete();
    }

    public void ITookIt(String idProduct, iWasTookIt listener) {
        modelFirebase.IWasTookIt(idProduct, listener);
    }

    // Return list of product by category
    public interface getProductsByCat {
        void onComplete(List<Product> ListOfProduct);
    }

    public void getProductsByCat(List<Product> ListOfProduct, String nameCat, getProductsByCat listener) {
        modelFirebase.getProductsByCat(ListOfProduct, nameCat, listener);
    }

    // Return list of all my product that i want
    public interface getProductsIWant {
        void onComplete(List<Product> listOfMyProduct);
    }

    public void GetProductsIWant(List<Product> listOfMyProduct, String name, getProductsIWant listener) {
        modelFirebase.GetProductsIWant(listOfMyProduct, name, listener);
    }

    // Return list of all my product that i uploaded
    public interface getProductsByMe {
        void onComplete(List<Product> ListOfProduct);
    }

    public void GetProductsByMe(List<Product> ListOfProduct, String name, getProductsByMe listener) {
        modelFirebase.GetProductsByMe(ListOfProduct, name, listener);
    }

    // Sign in
    public interface signIn {
        void onComplete(String message);
    }

    public void SignIn(String email, String password, signIn listener) {
        modelFirebase.SignIn(email, password, listener);
    }

    public interface getEmailCurrentUser {
        void onComplete(String email);
    }

    public void GetEmailCurrentUser(getEmailCurrentUser listener) {
        modelFirebase.GetEmailCurrentUser(listener);
    }

    public interface getPictureCurrentUser {
        void onComplete(String picture);
    }

    public void getPictureCurrentUser(String email,getPictureCurrentUser listener) {
        modelFirebase.GetEmailCurrentUser(email,listener);
    }
}


