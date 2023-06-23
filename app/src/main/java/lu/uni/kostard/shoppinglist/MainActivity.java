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
        mAdapter = new ShoppingListRecyclerViewAdapter(new ArrayList<>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyDatabase.getInstance(this).shoppingListDao().getAllItems().observe(this, items -> {
//            Use RecyclerView adapter to display items
            System.out.println("Items: " + items);
            mAdapter.setItems(items);
        });
    }

    public void startAddNewItem(View view) {
        Intent intent = new Intent(this, AddNewItemActivity.class);
        startActivity(intent);
    }
}