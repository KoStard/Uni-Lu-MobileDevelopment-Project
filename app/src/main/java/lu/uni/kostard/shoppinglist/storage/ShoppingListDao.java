package lu.uni.kostard.shoppinglist.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This class is the DAO for the shopping list items.
 */
@Dao
public interface ShoppingListDao {
    @Insert
    void insert(ShoppingListItem item);

    @Query("SELECT * FROM shopping_list_item")
    LiveData<List<ShoppingListItem>> getAllItems();

    @Query("SELECT * FROM shopping_list_item WHERE id = :id")
    ShoppingListItem getItemById(int id);

    @Delete
    void delete(ShoppingListItem item);

    @Update
    void update(ShoppingListItem item);
}
