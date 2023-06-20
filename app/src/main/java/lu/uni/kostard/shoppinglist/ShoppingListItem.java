package lu.uni.kostard.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {
    @PrimaryKey
    public int id;
//    TODO: Null checks
    public String title;
    public String description;
    public String quantity;
}
