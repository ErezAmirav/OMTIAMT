package Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ProductDao {

    @Query("select * from Product")
    List<Product> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertAll(Product... products);

    @Delete
    void delete(Product product);
}

