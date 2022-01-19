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



    public interface saveImageListener {
        void onComplete(String url);
    }

    public void saveImage(Bitmap imgBitmap, String imgName, saveImageListener listener) {
        modelFirebase.saveImg(imgBitmap, imgName, listener);
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

    public void SetProduct(Product product, setProduct listener) {
        modelFirebase.SetProduct(product, listener);
    }

    public void SignOut() {
        modelFirebase.SignOut();
    }
    public interface setTakenProduct {
        void onComplete();
    }

    public void setTakenProduct(String idProduct,String nameTaker, setTakenProduct listener) {
        modelFirebase.SetTakenProduct(idProduct,nameTaker, listener);
    }
    public interface dontNeedit {
        void onComplete();
    }

    public void DontNeedit(String idProduct, dontNeedit listener) {
        modelFirebase.DontNeedit(idProduct, listener);
    }
    public interface getProductsByCat {
        void onComplete(List<Product> ListOfProduct);
    }

    public HashMap<String, String> getProductsByCat(List<Product> ListOfProduct, String nameCat, getProductsByCat listener) {
        modelFirebase.getProductsByCat(ListOfProduct, nameCat, listener);
        return null;
    }
    public interface getProductsIwant {
        void onComplete(List<Product> listOfMyProduct);
    }

    public void GetProductsIwant(List<Product> listOfMyProduct, String name, getProductsIwant listener) {
        modelFirebase.GetProductsIwant(listOfMyProduct, name, listener);
    }
    public interface getProductsByMe {
        void onComplete(List<Product> ListOfProduct);
    }

    public HashMap<String, String> GetProductsByMe(List<Product> ListOfProduct, String name, getProductsByMe listener) {
        modelFirebase.GetProductsByMe(ListOfProduct, name, listener);
        return null;
    }

}


