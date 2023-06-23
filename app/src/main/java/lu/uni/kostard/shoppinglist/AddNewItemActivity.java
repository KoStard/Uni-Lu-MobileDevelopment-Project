package lu.uni.kostard.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lu.uni.kostard.shoppinglist.storage.MyDatabase;
import lu.uni.kostard.shoppinglist.storage.ShoppingListItem;

public class AddNewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
    }

    /**+
     * This method is called when the user clicks the save button, creates new item.
     */
    public void saveItem(View view) {
        EditText titleEditText = findViewById(R.id.itemName);
        String title = titleEditText.getText().toString();
        EditText descriptionEditText = findViewById(R.id.itemDetails);
        String description = descriptionEditText.getText().toString();
        EditText quantityEditText = findViewById(R.id.itemQuantity);
        String quantity = quantityEditText.getText().toString();
        ShoppingListItem item = new ShoppingListItem();
        item.title = title;
        item.description = description;
        item.quantity = quantity;
        new Thread(() -> {
            MyDatabase.getInstance(this).shoppingListDao().insert(item);
            runOnUiThread(() -> {
                Toast.makeText(this, "New item saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}