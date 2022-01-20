package com.example.omtiamt.Model.Data;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.omtiamt.Model.Classes.Product;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class model {
    public static final model instance = new model();
    ModelFirebase modelFirebase = new ModelFirebase();

    private model() {
    }
    //Delete User and all the Product he have
    public interface deleteuser {
        void onComplete();
    }
    public void Deleteuser(deleteuser listener) {
        modelFirebase.Deleteuser(listener);
    }

    //Add new Product
    public interface addProductListener {
        void onComplete();
    }
    public void addProduct(Product product, addProductListener listener) {
        modelFirebase.addProduct(product, listener);
    }
    //Get Product by id and return the Product
    public interface getProductListener {
        void onComplete(Product product);
    }
    public void getProduct(String id, Product product, getProductListener listener) {
        modelFirebase.getProduct(id,product,listener);
    }

    //Register New User
    public void registerNewUser(String email, String password) {
        modelFirebase.registerNewUser(email, password);
    }
    //check if the email exist
    public boolean checkEmail(String email) {
        return modelFirebase.checkEmail(email);
    }

    //Get all the name and pictures of Category by id - to HashMap
    public interface getCatNameAndPictures {
        void onComplete(HashMap<String, String> catHash);
    }
    public void getCatNameAndPictures(HashMap<String, String> catHash, getCatNameAndPictures listener) {
        modelFirebase.getCatNameAndPictures(catHash, listener);
    }
    //Save Image in Firebase
    public interface saveImageListener {
        void onComplete(String url);
    }
    public void saveImage(Bitmap imgBitmap, String imgName, saveImageListener listener) {
        modelFirebase.saveImg(imgBitmap, imgName, listener);
    }
    //Delete one Product by id
    public interface deleteProduct {
        void onComplete();
    }
    public void DeleteProduct(String id, deleteProduct listener) {
        modelFirebase.DeleteProduct(id, listener);
    }
    //Set Product
    public interface setProduct {
        void onComplete();
    }
    public void SetProduct(Product product, setProduct listener) {
        modelFirebase.SetProduct(product, listener);
    }
    //SignOut
    public void SignOut() {
        modelFirebase.SignOut();
    }
    //Set the product taker
    public interface setTakenProduct {
        void onComplete();
    }
    public void setTakenProduct(String idProduct,String nameTaker, setTakenProduct listener) {
        modelFirebase.SetTakenProduct(idProduct,nameTaker, listener);
    }
    //Dont need the product and return him to show for all
    public interface dontNeedit {
        void onComplete();
    }
    public void DontNeedit(String idProduct, dontNeedit listener) {
        modelFirebase.DontNeedit(idProduct, listener);
    }
    // The product not available anymore
    public interface iWasTookit {
        void onComplete();
    }
    public void ITookit(String idProduct, iWasTookit listener) {
        modelFirebase.IWasTookit(idProduct, listener);
    }
    //Return list of product by name category
    public interface getProductsByCat {
        void onComplete(List<Product> ListOfProduct);
    }
    public void getProductsByCat(List<Product> ListOfProduct, String nameCat, getProductsByCat listener) {
        modelFirebase.getProductsByCat(ListOfProduct, nameCat, listener);
    }
    //Return list of all my product that i want
    public interface getProductsIwant {
        void onComplete(List<Product> listOfMyProduct);
    }
    public void GetProductsIwant(List<Product> listOfMyProduct, String name, getProductsIwant listener) {
        modelFirebase.GetProductsIwant(listOfMyProduct, name, listener);
    }
    //Return list of all my product that i uploaded
    public interface getProductsByMe {
        void onComplete(List<Product> ListOfProduct);
    }
    public void GetProductsByMe(List<Product> ListOfProduct, String name, getProductsByMe listener) {
        modelFirebase.GetProductsByMe(ListOfProduct, name, listener);
    }
    //Sign in
    public interface signIn {
        void onComplete(String message);
    }
    public void SignIn(String email,String password, signIn listener) {
        modelFirebase.SignIn(email,password, listener);
    }


}


