/**
 * RecyclerView Adapter for displaying favorite locations.
 * Handles the layout and interaction of list items.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

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
    private final OnItemClickListener clickListener;

    // Constructor
    public FavoritesAdapter(List<FavoriteLocation> favorites, OnItemLongClickListener longClickListener, OnItemClickListener clickListener) {
        this.favorites = favorites;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(favorites.get(position), longClickListener, clickListener);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    // Static ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView latitudeText;
        TextView longitudeText;

        public ViewHolder(View itemView) {
            super(itemView);
            latitudeText = itemView.findViewById(R.id.latitude_text);
            longitudeText = itemView.findViewById(R.id.longitude_text);
        }

        public void bind(final FavoriteLocation favorite, final OnItemLongClickListener longClickListener, final OnItemClickListener clickListener) {
            String latText = itemView.getContext().getString(R.string.latitude_text, String.valueOf(favorite.latitude));
            String lngText = itemView.getContext().getString(R.string.longitude_text, String.valueOf(favorite.longitude));
            latitudeText.setText(latText);
            longitudeText.setText(lngText);

            itemView.setOnLongClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    longClickListener.onItemLongClicked(position);
                }
                return true; // Click was handled
            });

            itemView.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClicked(favorite);
                }
            });
        }
    }

    // Interface for long-click listener
    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    // Interface for click listener
    public interface OnItemClickListener {
        void onItemClicked(FavoriteLocation location);
    }
}
