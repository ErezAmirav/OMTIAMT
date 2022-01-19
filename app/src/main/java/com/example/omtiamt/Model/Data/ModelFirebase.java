package com.example.omtiamt.Model.Data;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.omtiamt.Model.Classes.Categories;
import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Classes.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public HashMap<String, String> catHash = new HashMap<>();
    public HashMap<String, String> catOrderByname = new HashMap<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public void getAllUsers(model.getAllUsersListener listener) {
        db.collection(Users.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<Users> list = new LinkedList<Users>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Users user = Users.create(doc.getData());
                            if (user != null)
                                list.add(user);
                        }
                    }
                    listener.onComplete(list);
                });
    }

    public void addUser(Users user, model.addUsersListener listener) {
        Map<String, Object> json = user.toJson();
        String NewDocument = db.collection(Users.COLLECTION_NAME).document().getId().toString();
        json.put("id", NewDocument);
        db.collection(Users.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void addProduct(Product product, model.addProductListener listener) {
        Map<String, Object> json = product.toJson();
        String NewDocument = db.collection(Users.COLLECTION_NAME).document().getId().toString();
        json.put("id", NewDocument);
        db.collection(Product.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void registerNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Tag", "Succses");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Log.w("Tag", "Fail");
                    }
                });
    }

    public void getUsersById(String userId) {
    }


    boolean check;

    public boolean checkEmail(String email) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            check = !task.getResult().getSignInMethods().isEmpty();
        });
        return check;
    }

    public List<String> getCatName() {
        List<String> list = new ArrayList<>();
        CollectionReference applicationsRef = db.collection(Categories.COLLECTION_NAME);
        db.collection(Categories.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    DocumentReference applicationIdRef = applicationsRef.document(id);
                    String name = document.getString("Name");
                    list.add(name);
                }
                Log.d("TAG", list.toString());
            }
        });
        return list;
    }

    //get all categories names and pictures
    public void getCatNameAndPictures(HashMap<String, String> catHash, model.getCatNameAndPictures listener) {
        db.collection("Category").get().addOnCompleteListener(task -> {
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
    //get Product with id
    public void getProduct(String id, Product product, model.getProductListener listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String Category = document.getString("id");
                    if (Category.equals(id)) {
                        product.setId(document.getId());
                        product.setAvailable(document.getBoolean("isAvailable"));
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
    //get all the products by the name of category
    public void getProductsByCat(List<Product> ListOfProduct, String nameCat, model.getProductsByCat listener) {
        ListOfProduct.clear();
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if(nameCat.equals("View All")) {
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
                            Product product = new Product(id, userName,category, name, picture, details, location);
                            ListOfProduct.add(product);
                        }
                    }
                    listener.onComplete(ListOfProduct);
                }
            }
        });
    }
    public void GetProductsIwant(List<Product> ListOfMyProduct, String name, model.getProductsIwant listener) {
        ListOfMyProduct.clear();
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

    public void saveImg(Bitmap imgBitmap, String imgName, model.saveImageListener listener) {
        StorageReference storageReference = storage.getReference();
        StorageReference imgRef = storageReference.child("/product_photos" + imgName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(e ->
                listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot ->
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUri = uri;
                            listener.onComplete(downloadUri.toString());
                        }));
    }

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

    public void SetProduct(Product product , model.setProduct listener) {

        db.collection(Product.COLLECTION_NAME).document(product.getId()).set(product.toJson())
                .addOnCompleteListener(task ->
                        listener.onComplete());
    }

    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void SetTakenProduct(String idProduct, String nameTaker, model.setTakenProduct listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot myDocument : task.getResult()) {
                    String idCat = myDocument.getString("id");
                    if (idCat.equals(idProduct)) {
                        db.collection(Product.COLLECTION_NAME).document(idCat).update("UserBuy",nameTaker);
                    }
                }
                listener.onComplete();
            }
        });

    }

    public void DontNeedit(String idProduct, model.dontNeedit listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot myDocument : task.getResult()) {
                    String idCat = myDocument.getString("id");
                    if (idCat.equals(idProduct)) {
                        db.collection(Product.COLLECTION_NAME).document(idCat).update("UserBuy","noBody");
                    }
                }
                listener.onComplete();
            }
        });
    }


    public void GetProductsByMe(List<Product> listOfMyProduct, String name, model.getProductsByMe listener) {
        listOfMyProduct.clear();
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

    public void Deleteuser(model.deleteuser listener) {
        String userEmail2 = mAuth.getCurrentUser().getEmail();
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String myName = document.getString("User");
                    if (userEmail2.equals(myName)) {
                        String id = document.getId();
                        db.collection(Product.COLLECTION_NAME).document(id).delete();
                    }
                }
                listener.onComplete();
                currentUser.delete();
            }
        });

    }
}
