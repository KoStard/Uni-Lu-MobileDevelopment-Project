package lu.uni.kostard.shoppinglist.storage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This class is the database for the shopping list items.
 * It is responsible for creating the database and providing the DAO.
 */
@Database(entities = ShoppingListItem.class, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance;
    public abstract ShoppingListDao shoppingListDao();

    public static MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                MyDatabase.class,
                "shoppinglist.db"
            )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
