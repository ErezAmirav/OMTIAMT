package com.example.omtiamt.Model.Data;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.omtiamt.Model.Classes.Categories;
import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Classes.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    boolean check;
    String myMessage;

    // Add new Product
    public void addProduct(Product product, model.addProductListener listener) {
        Map<String, Object> json = product.toJson();
        String NewDocument = db.collection(Product.COLLECTION_NAME).document().getId();
        json.put("id", NewDocument);
        db.collection(Product.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }
    public void AddUser(Users user, model.addUserListener listener) {
        Map<String, Object> json = user.toJson();
        String NewDocument = db.collection(Users.COLLECTION_NAME).document().getId();
        json.put("id", NewDocument);
        json.put("email", user.getEmail());
        db.collection(Users.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    // Register New User
    public void registerNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Tag", "Success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Log.w("Tag", "Fail");
                    }
                });
    }

    // Check if the email exists
    public boolean checkEmail(String email) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            check = !task.getResult().getSignInMethods().isEmpty();
        });
        return check;
    }

    // Get all the names and pictures of Category by id - to HashMap
    public void getCatNameAndPictures(HashMap<String, String> catHash, model.getCatNameAndPictures listener) {
        db.collection(Categories.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    String name = document.getString("Name");
                    String picture = document.getString("Picture");
                    catHash.put(name, picture);
                }
                String name = "View All";
                String pic = "https://i.ibb.co/nbTM1X5/viewall.png";
                catHash.put(name, pic);
                listener.onComplete(catHash);
            }
        });
    }

    // Get Product by id
    public void getProduct(String id, Product product, model.getProductListener listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String Category = document.getString("id");
                    if (Category.equals(id)) {
                        product.setId(document.getId());
                        product.setUser(document.getString("User"));
                        product.setCategory(document.getString("Category"));
                        product.setProductName(document.getString("Name"));
                        product.setProductPicUrl(document.getString("Picture"));
                        product.setDetails(document.getString("Details"));
                        product.setLocation(document.getString("Location"));
                        product.setUserBuy(document.getString("UserBuy"));
                    }
                }
                listener.onComplete(product);
            }
        });
    }

    // Get all the products by the name of the category
    public void getProductsByCat(List<Product> ListOfProduct, String nameCat, model.getProductsByCat listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (nameCat.equals("View All")) {
                        String buy = document.getString("UserBuy");
                        if (buy.equals("nobody")) {
                            String id = document.getId();
                            String userName = document.getString("User");
                            String name = document.getString("Name");
                            String picture = document.getString("Picture");
                            String details = document.getString("Details");
                            String category = document.getString("Category");
                            String location = document.getString("Location");
                            Product product = new Product(id, userName, category, name, picture, details, location);
                            ListOfProduct.add(product);
                        }
                    }
                    String category = document.getString("Category");
                    if (category.equals(nameCat)) {
                        String buy = document.getString("UserBuy");
                        if (buy.equals("nobody")) {
                            String id = document.getId();
                            String userName = document.getString("User");
                            String name = document.getString("Name");
                            String picture = document.getString("Picture");
                            String details = document.getString("Details");
                            String location = document.getString("Location");
                            Product product = new Product(id, userName, category, name, picture, details, location);
                            ListOfProduct.add(product);
                        }
                    }
                    listener.onComplete(ListOfProduct);
                }
            }
        });
    }

    // Return list of all the products user wants
    public void GetProductsIWant(List<Product> ListOfMyProduct, String name, model.getProductsIWant listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String buy = document.getString("UserBuy");
                    if (name.equals(buy)) {
                        String id = document.getId();
                        String userName = document.getString("User");
                        String nameProduct = document.getString("Name");
                        String picture = document.getString("Picture");
                        String details = document.getString("Details");
                        String category = document.getString("Category");
                        String location = document.getString("Location");
                        Product product = new Product(id, userName, category, nameProduct, picture, details, location);
                        ListOfMyProduct.add(product);
                    }
                }
                listener.onComplete(ListOfMyProduct);
            }
        });
    }

    // Save Image to Firebase
    public void saveImg(Bitmap imgBitmap, String imgName, model.saveImageListener listener) {
        StorageReference storageReference = storage.getReference();
        StorageReference imgRef = storageReference.child("/product_photos" + imgName);
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bAOS);
        byte[] data = bAOS.toByteArray();
        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(e ->
                listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot ->
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUri = uri;
                            listener.onComplete(downloadUri.toString());
                        }));
    }

    // Delete Product by id
    public void DeleteProduct(String id, model.deleteProduct listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot myDocument : task.getResult()) {
                    String idCat = myDocument.getString("id");
                    if (idCat.equals(id)) {
                        db.collection(Product.COLLECTION_NAME).document(idCat).delete();
                    }
                }
                listener.onComplete();
            }
        });
    }

    // Set Product
    public void SetProduct(Product product, model.setProduct listener) {
        db.collection(Product.COLLECTION_NAME).document(product.getId()).set(product.toJson())
                .addOnCompleteListener(task ->
                        listener.onComplete());
    }

    // SignOut
    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
    }

    // Set the product taker
    public void SetTakenProduct(String idProduct, String nameTaker, model.setTakenProduct listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot myDocument : task.getResult()) {
                    String idCat = myDocument.getString("id");
                    if (idCat.equals(idProduct)) {
                        db.collection(Product.COLLECTION_NAME).document(idCat).update("UserBuy", nameTaker);
                    }
                }
                listener.onComplete();
            }
        });

    }

    // Cancel saved product and return it to available list
    public void DontNeedIt(String idProduct, model.dontNeedIt listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot myDocument : task.getResult()) {
                    String idCat = myDocument.getString("id");
                    if (idCat.equals(idProduct)) {
                        db.collection(Product.COLLECTION_NAME).document(idCat).update("UserBuy", "nobody");
                    }
                }
                listener.onComplete();
            }
        });
    }

    // Return list of all my products that I uploaded
    public void GetProductsByMe(List<Product> listOfMyProduct, String name, model.getProductsByMe listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String myName = document.getString("User");
                    if (name.equals(myName)) {
                        String id = document.getId();
                        String nameProduct = document.getString("Name");
                        String picture = document.getString("Picture");
                        String details = document.getString("Details");
                        String category = document.getString("Category");
                        String location = document.getString("Location");
                        Product product = new Product(id, myName, category, nameProduct, picture, details, location);
                        listOfMyProduct.add(product);
                    }
                }
                listener.onComplete(listOfMyProduct);
            }
        });
    }

    // Delete User and all his products
    public void DeleteUser(model.deleteUser listener) {
        String email = mAuth.getCurrentUser().getEmail();
        db.collection(Users.COLLECTION_NAME).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String myName = document.getString("email");
                            if (email.equals(myName)) {
                                String id = document.getId();
                                db.collection(Users.COLLECTION_NAME).document(id).delete();
                            }
                        }
                    }
                });
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String myName = document.getString("User");
                    if (email.equals(myName)) {
                        String id = document.getId();
                        db.collection(Product.COLLECTION_NAME).document(id).delete();
                    }
                }
                listener.onComplete();
            }
        });
    }

    // The product not available anymore
    public void IWasTookIt(String idProduct, model.iWasTookIt listener) {
        db.collection(Product.COLLECTION_NAME).document(idProduct).delete();
        listener.onComplete();

    }

    // Sign in
    public void SignIn(String email, String password, model.signIn listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                myMessage = "SUCCESS";
            } else {
                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                switch (errorCode) {
                    case "ERROR_INVALID_EMAIL":
                        myMessage = "ERROR_INVALID_EMAIL";

                    case "ERROR_WRONG_PASSWORD":
                        myMessage = "ERROR_WRONG_PASSWORD";
                        break;

                    case "ERROR_USER_NOT_FOUND":
                        myMessage = "ERROR_USER_NOT_FOUND";
                        break;
                }
            }
            listener.onComplete(myMessage);
        });

    }

    public void GetEmailCurrentUser(model.getEmailCurrentUser listener) {
        listener.onComplete(currentUser.getEmail());


    }

    public void GetEmailCurrentUser(String email, model.getPictureCurrentUser listener) {
        db.collection(Users.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String myEmail = document.getString("email");
                    if (email.equals(myEmail)) {
                        String imageUrl = document.getString("imageUrl");
                        listener.onComplete(imageUrl);
                    }
                }
            }
        });
    }
}