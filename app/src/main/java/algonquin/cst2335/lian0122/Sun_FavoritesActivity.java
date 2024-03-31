/**
 * Activity for displaying and managing favorite locations.
 * Allows users to view, add, and delete favorites.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import androidx.room.Room;

/**
 * Activity for displaying and managing favorite locations.
 * Allows users to view, add, and delete favorite locations.
 */
public class Sun_FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnItemLongClickListener, FavoritesAdapter.OnItemClickListener {

    /**
     * RecyclerView for displaying the list of favorite locations.
     */
    private RecyclerView recyclerView;

    /**
     * Adapter for the RecyclerView to manage and display favorite locations.
     */
    private FavoritesAdapter adapter;

    /**
     * Database instance for accessing favorite locations.
     */
    private Sun_AppDatabase db;

    /**
     * List to hold favorite location data retrieved from the database.
     */
    private List<FavoriteLocation> favoriteLocations;

    /**
     * Initializes the activity, sets up the user interface and database connection.
     * @param savedInstanceState If the activity is being re-initialized after being previously shut down, this contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sun_favorites);

        ToolbarUtils.setupToolbar(this, R.id.toolbarFavorites);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }


        initializeRecyclerView();
        initializeDatabase();
    }


    /**
     * Initializes the RecyclerView for displaying favorite locations.
     */
    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Initializes the database and loads favorite locations.
     */
    private void initializeDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), Sun_AppDatabase.class, "favorite_locations_db").build();
        loadFavoriteLocations();
    }

    /**
     * Loads favorite locations from the database and displays them in the RecyclerView.
     */
    private void loadFavoriteLocations() {
        new Thread(() -> {
            favoriteLocations = db.locationDao().getAllLocations();
            runOnUiThread(() -> {
                adapter = new FavoritesAdapter(favoriteLocations, this, this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    /**
     * Initializes the options menu for the activity.
     * @param menu The options menu in which items are placed.
     * @return True for the menu to be displayed; false to not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sun_menu_main, menu);
        return true;
    }

    /**
     * Handles item selection from the options menu.
     * @param item The menu item that was selected.
     * @return False to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return ToolbarUtils.handleMenuItem(this, item) || super.onOptionsItemSelected(item);
    }

    /**
     * Handles long-click events on items in the RecyclerView.
     * @param position Position of the item that was long-clicked.
     */
    @Override
    public void onItemLongClicked(int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sun_delete_entry)
                .setMessage(R.string.sun_are_you_sure_you_want_to_delete_this_entry)
                .setPositiveButton(R.string.sun_delete_now, (dialog, which) -> deleteFavoriteLocation(position))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }


    /**
     * Deletes a favorite location from the database and updates the UI.
     * @param position Position of the item to be deleted.
     */
    private void deleteFavoriteLocation(int position) {
        FavoriteLocation locationToDelete = favoriteLocations.get(position);

        new Thread(() -> {
            db.locationDao().delete(locationToDelete);
            runOnUiThread(() -> {
                favoriteLocations.remove(position);
                adapter.notifyItemRemoved(position);
            });
        }).start();

        Snackbar.make(recyclerView, getString(R.string.sun_item_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.sun_undo), view -> undoDelete(locationToDelete, position))
                .show();

    }

    /**
     * Undoes the deletion of a favorite location.
     * @param location The FavoriteLocation to be reinstated.
     * @param position The position where the location should be inserted in the list.
     */
    private void undoDelete(FavoriteLocation location, int position) {
        new Thread(() -> {
            db.locationDao().insert(location);
            runOnUiThread(() -> {
                favoriteLocations.add(position, location);
                adapter.notifyItemInserted(position);
            });
        }).start();
    }

    /**
     * Handles click events on items in the RecyclerView.
     * @param location The FavoriteLocation object associated with the clicked item.
     */
    @Override
    public void onItemClicked(FavoriteLocation location) {
        Intent intent = new Intent(this, Sun_MainActivity.class);
        intent.putExtra(getString(R.string.sun_latitude), location.latitude);
        intent.putExtra(getString(R.string.sun_longitude), location.longitude);
        startActivity(intent);
    }

}
