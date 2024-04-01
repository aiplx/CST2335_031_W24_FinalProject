/**
 * RecyclerView Adapter for displaying favorite locations.
 * Handles the layout and interaction of list items.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122.Sun;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import algonquin.cst2335.lian0122.R;

/**
 * Adapter for managing a RecyclerView displaying favorite locations.
 * Handles the binding of data to views and user interactions with list items.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    /**
     * List of favorite locations to be displayed in the RecyclerView.
     */
    private final List<FavoriteLocation> favorites;

    /**
     * Listener for handling long clicks on RecyclerView items.
     */
    private final OnItemLongClickListener longClickListener;

    /**
     * Listener for handling regular clicks on RecyclerView items.
     */
    private final OnItemClickListener clickListener;

    /**
     * Constructs a FavoritesAdapter.
     * @param favorites List of FavoriteLocation objects to display.
     * @param longClickListener Listener for long clicks on items.
     * @param clickListener Listener for regular clicks on items.
     */
    public FavoritesAdapter(List<FavoriteLocation> favorites, OnItemLongClickListener longClickListener, OnItemClickListener clickListener) {
        this.favorites = favorites;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_location_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(favorites.get(position), longClickListener, clickListener);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return favorites.size();
    }

    /**
     * ViewHolder for the RecyclerView items in FavoritesAdapter.
     * This class holds the views for each item and binds data to them.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView for displaying the latitude of the location.
         */
        TextView latitudeText;

        /**
         * TextView for displaying the longitude of the location.
         */
        TextView longitudeText;

        /**
         * Constructor for ViewHolder.
         * Initializes the view elements of the RecyclerView item.
         * @param itemView View for the RecyclerView item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            latitudeText = itemView.findViewById(R.id.latitude_text);
            longitudeText = itemView.findViewById(R.id.longitude_text);
        }

        /**
         * Binds a FavoriteLocation object to the item view.
         * Sets up the display of latitude, longitude, and event listeners for the item.
         * @param favorite The FavoriteLocation object to be displayed in this item.
         * @param longClickListener Listener for long click events.
         * @param clickListener Listener for click events.
         */
        public void bind(final FavoriteLocation favorite, final OnItemLongClickListener longClickListener, final OnItemClickListener clickListener) {
            String latText = itemView.getContext().getString(R.string.sun_latitude_text, String.valueOf(favorite.latitude));
            String lngText = itemView.getContext().getString(R.string.sun_longitude_text, String.valueOf(favorite.longitude));
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

    /**
     * Interface definition for a callback to be invoked when an item in the RecyclerView
     * has been long clicked.
     */
    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    /**
     * Interface definition for a callback to be invoked when an item in the RecyclerView
     * has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Called when an item has been clicked.
         * @param location The FavoriteLocation of the item that was clicked.
         */
        void onItemClicked(FavoriteLocation location);
    }
}
