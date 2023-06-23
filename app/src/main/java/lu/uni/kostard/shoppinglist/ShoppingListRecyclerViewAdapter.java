package lu.uni.kostard.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private List<ShoppingListItem> items;

    public ShoppingListRecyclerViewAdapter(List<ShoppingListItem> items) {
        this.items = items;
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
    }
}
