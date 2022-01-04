package com.example.omtiamt.Model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class model {
    public static final model instance = new model();
    Executor executors = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    ModelFirebase modelFirebase = new ModelFirebase();
    private model() {
    }
        public interface GetAllUsersListener{
            void onComplete(List<Users> user);
        }

        public void getAllUsers(GetAllUsersListener listener){
        modelFirebase.getAllUsers(listener);
        }
        public interface GetAllCategoriesListener{
        void onComplete(List<Categories> categories);
    }

        public List<String> getAllCategoriesName(){
        return modelFirebase.getCategoriesName();
    }

        public interface AddUsersListener{
        void onComplete();
        }
        public interface AddProductListener{
        void onComplete();
        }

        public void addUser(Users user, AddUsersListener listener){
        modelFirebase.addUser(user,listener);
        }


    public void addProduct(Product product, AddProductListener listener){
        modelFirebase.addProduct(product,listener);
    }


        public Users getUsersById(String userId){
        modelFirebase.getUsersById(userId);
        return null;
        }
    public void registerNewUser(String email,String password){
        modelFirebase.registerNewUser(email,password);
    }

    }


