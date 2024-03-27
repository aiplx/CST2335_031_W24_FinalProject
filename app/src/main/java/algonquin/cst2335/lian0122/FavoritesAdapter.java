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
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_location_item, parent, false);
        return new ViewHolder(view, longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at the current position
        FavoriteLocation favorite = favorites.get(position);

        // Set the data to the view
        holder.latitudeText.setText("Lat: " + favorite.latitude);
        holder.longitudeText.setText("Lng: " + favorite.longitude);
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

            // Set up the long-click listener
            itemView.setOnLongClickListener(view -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
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
