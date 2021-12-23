package com.example.omtiamt.Model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class model {
    public static final model instance = new model();
    ExecutorService executors = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    ModelFirebase modelFirebase = new ModelFirebase();

    private model() {
    }
        public interface GetAllUsersListener{
            void onComplete(List<Users> user);
        }

        public void getAllUsers(GetAllUsersListener listener){
        ModelFirebase.getAllUsers(listener);
        }
        public interface AddUsersListener{
        void onComplete(List<Users> user);
    }

        public void addUser(Users user, AddUsersListener listener){
        ModelFirebase.addUser(user,listener);
        }

        public Users getUsersById(String userId){
        ModelFirebase.getUsersById(userId);
        return null;
        }

    }

