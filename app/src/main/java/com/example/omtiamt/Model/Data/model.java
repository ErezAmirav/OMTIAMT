package com.example.omtiamt.Model.Data;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.core.os.HandlerCompat;

import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Classes.Users;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class model {
    public static final model instance = new model();
    Executor executors = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    ModelFirebase modelFirebase = new ModelFirebase();

    private model() {
    }

    public interface getAllUsersListener {
        void onComplete(List<Users> user);
    }

    public List<String> getAllCategoriesName() {
        return modelFirebase.getCatName();
    }

    public interface addUsersListener {
        void onComplete();
    }

    public interface addProductListener {
        void onComplete();
    }
    public interface getProductListener {
        void onComplete(Product product);
    }
    public interface setProductListener {
        void onComplete(Product product);
    }


    public void addUser(Users user, addUsersListener listener) {
        modelFirebase.addUser(user, listener);
    }

    public void addProduct(Product product, addProductListener listener) {
        modelFirebase.addProduct(product, listener);
    }

    public Users getUsersById(String userId) {
        modelFirebase.getUsersById(userId);
        return null;
    }
    public Product getProduct(String id, Product product, getProductListener listener) {
        modelFirebase.getProduct(id,product,listener);
        return null;
    }

    public void registerNewUser(String email, String password) {
        modelFirebase.registerNewUser(email, password);
    }

    public boolean checkEmail(String email) {
        return modelFirebase.checkEmail(email);
    }

    public List<String> getCategoryNames() {
        return modelFirebase.getCatName();
    }

    /*public HashMap<String, String> getCatNameAndPictures() {
        return modelFirebase.getCatNameAndPictures();
    }*/


    public interface getCatNameAndPictures {
        void onComplete(HashMap<String, String> catHash);
    }

    public HashMap<String, String> getCatNameAndPictures(HashMap<String, String> catHash, getCatNameAndPictures listener) {
        modelFirebase.getCatNameAndPictures(catHash, listener);
        return null;
    }

    public interface getProductsByCat {
        void onComplete(List<Product> ListOfProduct);
    }

    public HashMap<String, String> getProductsByCat(List<Product> ListOfProduct, String nameCat, getProductsByCat listener) {
        modelFirebase.getProductsByCat(ListOfProduct, nameCat, listener);
        return null;
    }

    public interface saveImageListener {
        void onComplete(String url);
    }

    public void saveImage(Bitmap imgBitmap, String imgName, saveImageListener listener) {
        modelFirebase.saveImg(imgBitmap, imgName, listener);
    }

    public interface getMyProducts {
        void onComplete(HashMap<String, String> catHash);
    }

    public HashMap<String, String> getMyProducts(HashMap<String, String> catHash, String myName, getMyProducts listener) {
        modelFirebase.getMyProducts(catHash, myName, listener);
        return null;
    }

    public interface getTheProductsIWant {
        void onComplete(HashMap<String, String> catHash);
    }

    public HashMap<String, String> getTheProductsIWant(HashMap<String, String> catHash, String myName, getTheProductsIWant listener) {
        modelFirebase.getTheProductsIWant(catHash, myName, listener);
        return null;
    }
    public interface deleteProduct {
        void onComplete();
    }

    public void DeleteProduct(String id, deleteProduct listener) {
        modelFirebase.DeleteProduct(id, listener);
    }
    public interface setProduct {
        void onComplete();
    }

    public void SetProduct(String idProduct,String nameProduuct,String addressProduct,String detailsProduct,String pictureUrl, setProduct listener) {
        modelFirebase.SetProduct(idProduct,nameProduuct,addressProduct,detailsProduct,pictureUrl, listener);
    }

    public interface signOut {
        void onComplete();
    }

    public void SignOut(signOut listener) {
        modelFirebase.SignOut(listener);
    }
}


