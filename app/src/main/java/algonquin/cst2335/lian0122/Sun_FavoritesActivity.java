package algonquin.cst2335.lian0122;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Sun_FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sun_favorites);

        // Setting up toolbar using the utility class
        ToolbarUtils.setupToolbar(this, R.id.toolbarFavorites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflating the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handling item selection using ToolbarUtils
        return ToolbarUtils.handleMenuItem(this, item) || super.onOptionsItemSelected(item);
    }
}
