package com.example.omtiamt.Model.Data;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.omtiamt.Model.Classes.Categories;
import com.example.omtiamt.Model.Classes.Product;
import com.example.omtiamt.Model.Classes.Users;
import com.example.omtiamt.Model.Data.model;
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
    public HashMap<String, String> allMyProducts = new HashMap<>();
    List<Product> ListOfProduct = new LinkedList<>();
    public HashMap<String, String> theProductsIWant = new HashMap<>();

    public void getAllUsers(model.GetAllUsersListener listener) {
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

    public void addUser(Users user, model.AddUsersListener listener) {
        Map<String, Object> json = user.toJson();
        String NewDocument = db.collection(Users.COLLECTION_NAME).document().getId().toString();
        json.put("id", NewDocument);
        db.collection(Users.COLLECTION_NAME)
                .document(NewDocument)
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void addProduct(Product product, model.AddProductListener listener) {
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
    public void getCatNameAndPictures(HashMap<String, String> catHash, model.GetCatNameAndPictures listener) {
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

    public void getProduct(String id,Product product,model.GetProductListener listener)
    {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String Category = document.getString("id");
                    if (Category.equals(id)) {
                        /*String id = document.getId();
                        Boolean isAvailable = document.getBoolean("isAvailable");
                        String userName = document.getString("User");
                        String category = document.getString("Category");
                        String name = document.getString("Name");
                        String picture = document.getString("Picture");
                        String details = document.getString("Details");
                        String location = document.getString("Location");
                        Product myp = new Product(id,name,category,location,details,userName,isAvailable,picture,null);

                         */
                        product.setId(document.getId());
                        product.setAvailable(document.getBoolean("isAvailable"));
                        product.setUser(document.getString("User"));
                        product.setCategory(document.getString("Category"));
                        product.setProductName(document.getString("Name"));
                        product.setProductPicUrl(document.getString("Picture"));
                        product.setDetails(document.getString("Details"));
                        product.setLoaction(document.getString("Location"));

                    }
                }
                listener.onComplete(product);
            }
        });

    }

    //get all the products by the name of category
    public void getProductsByCat(List<Product> ListOfProduct, String nameCat, model.GetProductsByCat listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String Category = document.getString("Category");
                    if (Category.equals(nameCat)) {
                        String id = document.getId();
                        String userName = document.getString("User");
                        String name = document.getString("Name");
                        String picture = document.getString("Picture");
                        String details = document.getString("Details");
                        String location = document.getString("Location");
                        Product product = new Product(id,userName,name,picture,details,location);
                        ListOfProduct.add(product);

                    }
                }
                listener.onComplete(ListOfProduct);
            }
        });
    }

    public void getMyProducts(HashMap<String, String> catHash, String myName, model.GetmyProducts listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String userName = document.getString("User");
                    if (userName.equals(myName)) {
                        String id = document.getId();
                        String name = document.getString("Name");
                        String picture = document.getString("Picture");
                        String details = document.getString("Details");
                        String location = document.getString("Location");
                        String isAvailable = document.getString("isAvailable");
                        allMyProducts.put("id", id);
                        allMyProducts.put("Name", name);
                        allMyProducts.put("Details", details);
                        allMyProducts.put("Picture", picture);
                        allMyProducts.put("Location", location);
                        allMyProducts.put("isAvailable", isAvailable);
                    }
                }
                listener.onComplete(catHash);
            }
        });
    }

    public void getTheProductsIWant(HashMap<String, String> catHash, String myName, model.GetTheProductsIWant listener) {
        db.collection(Product.COLLECTION_NAME).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String userBuy = document.getString("UserBuy");
                    if (userBuy.equals(myName)) {
                        String id = document.getId();
                        String name = document.getString("Name");
                        String userName = document.getString("User");
                        String picture = document.getString("Picture");
                        String details = document.getString("Details");
                        String location = document.getString("Location");
                        String isAvailable = document.getString("isAvailable");
                        allMyProducts.put("id", id);
                        allMyProducts.put("Name", name);
                        allMyProducts.put("User", userName);
                        allMyProducts.put("Details", details);
                        allMyProducts.put("Picture", picture);
                        allMyProducts.put("Location", location);
                        allMyProducts.put("isAvailable", isAvailable);
                    }
                }
                listener.onComplete(catHash);
            }
        });
    }


    public HashMap<String, String> catHashTest = new HashMap<String, String>();

    private HashMap<String, String> isFinish(HashMap<String, String> catHash, HashMap<String, String> inputHash) {
        inputHash = catHash;
        return inputHash;
    }


    public void saveImg(Bitmap imgBitmap, String imgName, model.SaveImageListener listener) {
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
}