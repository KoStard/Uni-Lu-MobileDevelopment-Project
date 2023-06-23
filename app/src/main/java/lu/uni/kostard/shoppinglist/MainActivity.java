package lu.uni.kostard.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected ShoppingListRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.shoppingListRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<ShoppingListItem> items = new ArrayList<>();
        items.add(new ShoppingListItem("Milk", "1L", "1"));
        items.add(new ShoppingListItem("Bread", "1kg", "2"));
        items.add(new ShoppingListItem("Eggs", "6", "3"));
        mAdapter = new ShoppingListRecyclerViewAdapter(items);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyDatabase.getInstance(this).shoppingListDao().getAllItems().observe(this, items -> {
//            Use RecyclerView adapter to display items

        });
    }

    public void startAddNewItem(View view) {
        Intent intent = new Intent(this, AddNewItemActivity.class);
        startActivity(intent);
    }
}