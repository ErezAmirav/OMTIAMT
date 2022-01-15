package com.example.omtiamt.Model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.core.os.HandlerCompat;
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

    private model() { }

    public interface GetAllUsersListener {
        void onComplete(List<Users> user);
    }

    public void getAllUsers(GetAllUsersListener listener) {
        modelFirebase.getAllUsers(listener);
    }

    public List<String> getAllCategoriesName() {
        return modelFirebase.getCatName();
    }

    public interface AddUsersListener {
        void onComplete();
    }

    public interface AddProductListener {
        void onComplete();
    }

    public void addUser(Users user, AddUsersListener listener) {
        modelFirebase.addUser(user, listener);
    }

    public void addProduct(Product product, AddProductListener listener) {
        modelFirebase.addProduct(product, listener);
    }

    public Users getUsersById(String userId) {
        modelFirebase.getUsersById(userId);
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

    public void urlToImg(String url, ImageView img) {
        Picasso.with(img.getContext()).load(url).into(img);
    }
    public interface GetCatNameAndPictures {
        void onComplete(HashMap<String,String> catHash);
    }
    public HashMap<String, String> getCatNameAndPictures(HashMap<String,String> catHash, GetCatNameAndPictures listener) {
        modelFirebase.getCatNameAndPictures(catHash,listener);
        return null;
    }
    public interface SaveImageListener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap imgBitmap, String imgName, SaveImageListener listener){
        modelFirebase.saveImg (imgBitmap, imgName, listener);
    }
}


