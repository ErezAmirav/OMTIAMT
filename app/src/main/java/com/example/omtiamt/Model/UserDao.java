package com.example.omtiamt.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface UserDao {

    @Query("select * from Users")
    List<Users> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertAll(Users... users);

    @Delete
    void delete(Users users);
}

