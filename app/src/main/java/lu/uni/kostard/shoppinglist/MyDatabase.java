package lu.uni.kostard.shoppinglist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ShoppingListItem.class, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance;
    public abstract ShoppingListDao shoppingListDao();

    public static MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                MyDatabase.class,
                "shoppinglist.db"
            ).build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
