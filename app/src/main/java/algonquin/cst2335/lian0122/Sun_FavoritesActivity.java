package algonquin.cst2335.lian0122;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import androidx.room.Room;

public class Sun_FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnItemLongClickListener, FavoritesAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private AppDatabase db;
    private List<FavoriteLocation> favoriteLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sun_favorites);

        ToolbarUtils.setupToolbar(this, R.id.toolbarFavorites);

        initializeRecyclerView();
        initializeDatabase();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeDatabase() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "favorite_locations_db").build();
        loadFavoriteLocations();
    }

    private void loadFavoriteLocations() {
        new Thread(() -> {
            favoriteLocations = db.locationDao().getAllLocations();
            runOnUiThread(() -> {
                adapter = new FavoritesAdapter(favoriteLocations, this, this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return ToolbarUtils.handleMenuItem(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemLongClicked(int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_entry)
                .setMessage(R.string.are_you_sure_you_want_to_delete_this_entry)
                .setPositiveButton(R.string.delete_now, (dialog, which) -> deleteFavoriteLocation(position))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void deleteFavoriteLocation(int position) {
        FavoriteLocation locationToDelete = favoriteLocations.get(position);

        new Thread(() -> {
            db.locationDao().delete(locationToDelete);
            runOnUiThread(() -> {
                favoriteLocations.remove(position);
                adapter.notifyItemRemoved(position);
            });
        }).start();

        Snackbar.make(recyclerView, getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), view -> undoDelete(locationToDelete, position))
                .show();

    }

    private void undoDelete(FavoriteLocation location, int position) {
        new Thread(() -> {
            db.locationDao().insert(location);
            runOnUiThread(() -> {
                favoriteLocations.add(position, location);
                adapter.notifyItemInserted(position);
            });
        }).start();
    }

    @Override
    public void onItemClicked(FavoriteLocation location) {
        // When an item is clicked, open Sun_MainActivity with the latitude and longitude for lookup
        Intent intent = new Intent(this, Sun_MainActivity.class);
        intent.putExtra(getString(R.string.Latitude), location.latitude);
        intent.putExtra(getString(R.string.Longitude), location.longitude);
        startActivity(intent);
    }
}
