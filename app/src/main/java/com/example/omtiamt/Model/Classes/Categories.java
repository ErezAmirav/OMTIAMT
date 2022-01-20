package com.example.omtiamt.Model.Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Categories {
    final public static String COLLECTION_NAME = "Category";
    @PrimaryKey
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
