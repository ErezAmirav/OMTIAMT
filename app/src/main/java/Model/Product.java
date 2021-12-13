package Model;

import android.graphics.Picture;

public class Product {
    String id;
    Users user;
    String productName;
    Categories category;
    Boolean isAvailable;
    String loaction;
    Picture productPicture;
    String Details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }

    public Picture getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(Picture productPicture) {
        this.productPicture = productPicture;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public Product(String id, Users user, String productName, Categories category, Boolean isAvailable, String loaction, Picture productPicture, String details) {
        this.id = id;
        this.user = user;
        this.productName = productName;
        this.category = category;
        this.isAvailable = isAvailable;
        this.loaction = loaction;
        this.productPicture = productPicture;
        Details = details;
    }
}
