package lu.uni.kostard.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private int itemId;

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
            MyDatabase.getInstance(this).shoppingListDao().update(item);
            runOnUiThread(this::finish);
        }).start();
    }
}