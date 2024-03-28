package algonquin.cst2335.lian0122;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<FavoriteLocation> favorites;
    private final OnItemLongClickListener longClickListener;

    // Constructor
    public FavoritesAdapter(List<FavoriteLocation> favorites, OnItemLongClickListener listener) {
        this.favorites = favorites;
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_location_item, parent, false);
        return new ViewHolder(view, longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteLocation favorite = favorites.get(position);

        // Using resource strings with placeholders
        String latText = holder.itemView.getContext().getString(R.string.latitude_text, String.valueOf(favorite.latitude));
        String lngText = holder.itemView.getContext().getString(R.string.longitude_text, String.valueOf(favorite.longitude));


        holder.latitudeText.setText(latText);
        holder.longitudeText.setText(lngText);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView latitudeText;
        TextView longitudeText;

        public ViewHolder(View itemView, OnItemLongClickListener longClickListener) {
            super(itemView);
            latitudeText = itemView.findViewById(R.id.latitude_text);
            longitudeText = itemView.findViewById(R.id.longitude_text);

            itemView.setOnLongClickListener(view -> {
                if (longClickListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClicked(position);
                    }
                }
                return true; // Click was handled
            });
        }
    }

    // Interface for long-click listener
    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }
}
