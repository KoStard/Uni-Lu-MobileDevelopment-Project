package lu.uni.kostard.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lu.uni.kostard.shoppinglist.storage.MyDatabase;
import lu.uni.kostard.shoppinglist.storage.ShoppingListItem;

public class EditItemActivity extends AppCompatActivity {
    private int itemId;

    /**
     * This method is called when the activity is created.
     * It is responsible for setting up the UI and populating the fields with the item data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemId = getIntent().getIntExtra("itemId", -1);
        if (itemId == -1) {
            throw new RuntimeException("No item id provided");
        }
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String quantity = getIntent().getStringExtra("quantity");

        EditText titleEditText = findViewById(R.id.itemName);
        titleEditText.setText(title);
        EditText descriptionEditText = findViewById(R.id.itemDetails);
        descriptionEditText.setText(description);
        EditText quantityEditText = findViewById(R.id.itemQuantity);
        quantityEditText.setText(quantity);

    }

    /**
     * This method is called when the user clicks the save button, updates existing item.
     */
    public void saveItem(View view) {
        EditText titleEditText = findViewById(R.id.itemName);
        String title = titleEditText.getText().toString();
        EditText descriptionEditText = findViewById(R.id.itemDetails);
        String description = descriptionEditText.getText().toString();
        EditText quantityEditText = findViewById(R.id.itemQuantity);
        String quantity = quantityEditText.getText().toString();
        ShoppingListItem item = new ShoppingListItem();
        item.id = itemId;
        item.title = title;
        item.description = description;
        item.quantity = quantity;
        new Thread(() -> {
            boolean successful;
            // Handling the exception, in case the item does not exist
            try {
                MyDatabase.getInstance(this).shoppingListDao().update(item);
                successful = true;
            } catch (Exception e) {
                e.printStackTrace();
                successful = false;
            }
            String message = successful ? "Item updated" : "Failed to update item";
            runOnUiThread(() -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}