package lu.uni.kostard.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import lu.uni.kostard.shoppinglist.storage.MyDatabase;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected ShoppingListRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up the RecyclerView
        mRecyclerView = findViewById(R.id.shoppingListRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ShoppingListRecyclerViewAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Getting the items from the database and continuously updating the UI when it changes
        MyDatabase.getInstance(this).shoppingListDao().getAllItems().observe(this, items -> {
            System.out.println("Items: " + items);
            mAdapter.setItems(items);
        });
    }

    /**
     * This method is called when the user clicks the add new item button.
     * It starts the AddNewItemActivity.
     */
    public void startAddNewItemActivity(View view) {
        Intent intent = new Intent(this, AddNewItemActivity.class);
        startActivity(intent);
    }
}