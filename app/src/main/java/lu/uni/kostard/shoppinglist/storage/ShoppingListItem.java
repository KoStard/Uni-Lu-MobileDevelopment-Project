package lu.uni.kostard.shoppinglist.storage;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class is the entity for the shopping list items.
 * It is responsible for defining the database table.
 */
@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    //    TODO: Null checks
    public String title;
    public String description;
    public String quantity;

    public ShoppingListItem() {
    }

    public ShoppingListItem(
            @NonNull String title,
            @NonNull String description,
            @NonNull String quantity
    ) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
    }
}
