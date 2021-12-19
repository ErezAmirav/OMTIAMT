package Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CategoriesDao {

    @Query("select * from Categories")
    List<Categories> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertAll(Categories... categories);

    @Delete
    void delete(Categories categories);
}
