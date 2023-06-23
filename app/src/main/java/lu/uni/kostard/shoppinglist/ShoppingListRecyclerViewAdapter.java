package lu.uni.kostard.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

    @Override
    public void onBindViewHolder(ShoppingListRecyclerViewAdapter.ViewHolder holder, int position) {
        ShoppingListItem item = items.get(position);
        holder.getTitleTextView().setText(item.title);
        holder.getDescriptionTextView().setText(item.description);
        holder.getQuantityTextView().setText(item.quantity);
        holder.bind(this);
    }

    // Because we are using Room database with live data, the content of the recycler view is updated
    public void deleteItem(int position) {
        ShoppingListItem item = items.get(position);
        new Thread(() -> {
            MyDatabase.getInstance(context).shoppingListDao().delete(item);
        }).start();
    }

    public void startEditItemActivity(int position) {
        ShoppingListItem item = items.get(position);
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("itemId", item.id);
        intent.putExtra("title", item.title);
        intent.putExtra("description", item.description);
        intent.putExtra("quantity", item.quantity);
        context.startActivity(intent);
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

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
            itemView.findViewById(R.id.deleteItemButton).setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                adapter.deleteItem(position);
            });
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
