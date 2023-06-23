package lu.uni.kostard.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lu.uni.kostard.shoppinglist.storage.MyDatabase;
import lu.uni.kostard.shoppinglist.storage.ShoppingListItem;


/**
 * This class is the adapter for the RecyclerView in MainActivity.
 * It is responsible for creating the views for the items in the list.
 */
public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private List<ShoppingListItem> items;
    private final Context context;

    public ShoppingListRecyclerViewAdapter(List<ShoppingListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ShoppingListRecyclerViewAdapter.ViewHolder holder, int position) {
        ShoppingListItem item = items.get(position);
        holder.getTitleTextView().setText(item.title);
        holder.getDescriptionTextView().setText(item.description);
        holder.getQuantityTextView().setText(item.quantity);
        holder.bind(this);
    }

    // Because we are using Room database with live data, the content of the recycler view is updated automatically
    public void deleteItem(int position) {
        ShoppingListItem item = items.get(position);
        new Thread(() -> {
            MyDatabase.getInstance(context).shoppingListDao().delete(item);
        }).start();
    }

    /**
     * This method is called when the user clicks on an item in the list.
     * It starts the EditItemActivity for editing the item.
     */
    public void startEditItemActivity(int position) {
        ShoppingListItem item = items.get(position);
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("itemId", item.id);
        intent.putExtra("title", item.title);
        intent.putExtra("description", item.description);
        intent.putExtra("quantity", item.quantity);
        context.startActivity(intent);
    }

    // This is not very optimal, but it is the easiest way to update the list for such small application
    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // This class is the ViewHolder for the RecyclerView.
    // It is responsible for holding the views for the items in the list.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public TextView getTitleTextView() {
            return itemView.findViewById(R.id.listItemTitle);
        }

        public TextView getDescriptionTextView() {
            return itemView.findViewById(R.id.listItemDescription);
        }

        public TextView getQuantityTextView() {
            return itemView.findViewById(R.id.listItemQuantity);
        }

        public void bind(ShoppingListRecyclerViewAdapter adapter) {
            // Set the click listeners for the buttons
            // The click listeners are set here, because the ViewHolder is created only once for each item

            // The delete button
            itemView.findViewById(R.id.deleteItemButton).setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                adapter.deleteItem(position);
                // This one is not guaranteed to be executed after the item is deleted, as the database operation is asynchronous
                // Can be solved by using other techniques, like AsyncTask, etc.
                Toast.makeText(adapter.context, "Item deleted", Toast.LENGTH_SHORT).show();
            });

            // The clicks on the item itself, editing the item
            itemView.findViewById(R.id.itemLinearLayout).setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                ShoppingListItem item = adapter.items.get(position);
                System.out.println("Edit Item: " + item);
                adapter.startEditItemActivity(position);
            });
        }
    }
}
