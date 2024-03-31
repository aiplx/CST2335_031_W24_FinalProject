/**
 * Main activity for the Sunrise & Sunset lookup feature.
 * Handles user inputs, API calls, and display of results.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import algonquin.cst2335.lian0122.databinding.ActivitySunMainBinding;

/**
 * Main activity for the Sunrise & Sunset lookup feature.
 * Handles user inputs, API calls, and the display of results.
 */
public class Sun_MainActivity extends AppCompatActivity {

    /**
     * API URL for fetching sunrise and sunset times.
     */
    private static final String API_URL = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=today&timezone=EST";

    /**
     * Shared preferences file name.
     */
    private static final String PREFS_NAME = "SunAppPrefs";

    /**
     * Binding instance for interacting with view elements.
     */
    private ActivitySunMainBinding binding;


    /**
     * Initializes the activity and sets up views and event handlers.
     * @param savedInstanceState Previous state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySunMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ToolbarUtils.setupToolbar(this, R.id.mainToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        loadPreferences();
        handleIncomingIntent();

        binding.btnSaveToFavorites.setOnClickListener(v -> {
            try {
                double lat = Double.parseDouble(binding.editTextLatitude.getText().toString());
                double lng = Double.parseDouble(binding.editTextLongitude.getText().toString());
                saveLocationToDatabase(lat, lng);
                savePreferences(String.valueOf(lat), String.valueOf(lng));
            } catch (NumberFormatException e) {
                Toast.makeText(Sun_MainActivity.this, R.string.invalid_lat_long, Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonLookup.setOnClickListener(v -> performSunriseSunsetLookup());
    }

    /**
     * Handles incoming intents and sets up views accordingly.
     */
    private void handleIncomingIntent() {
        if (getIntent().hasExtra(getString(R.string.latitude)) && getIntent().hasExtra(getString(R.string.longitude))) {
            double latitude = getIntent().getDoubleExtra(getString(R.string.latitude), 0);
            double longitude = getIntent().getDoubleExtra(getString(R.string.longitude), 0);
            binding.editTextLatitude.setText(String.valueOf(latitude));
            binding.editTextLongitude.setText(String.valueOf(longitude));
            performSunriseSunsetLookup();
        }
    }

    /**
     * Loads saved preferences (latitude and longitude).
     */
    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String latitude = prefs.getString((getString(R.string.latitude)), "");
        String longitude = prefs.getString((getString(R.string.longitude)), "");
        binding.editTextLatitude.setText(latitude);
        binding.editTextLongitude.setText(longitude);
    }

    /**
     * Saves latitude and longitude to preferences.
     * @param latitude  Latitude to save.
     * @param longitude Longitude to save.
     */
    private void savePreferences(String latitude, String longitude) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString((getString(R.string.latitude)), latitude);
        editor.putString((getString(R.string.longitude)), longitude);
        editor.apply();
    }

    /**
     * Performs the sunrise and sunset lookup operation.
     */
    private void performSunriseSunsetLookup() {
        String latitude = binding.editTextLatitude.getText().toString();
        String longitude = binding.editTextLongitude.getText().toString();
        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_both_lat_long, Toast.LENGTH_SHORT).show();
            return;
        }
        savePreferences(latitude, longitude);
        String url = String.format(API_URL, latitude, longitude);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                this::parseAndDisplayResult, this::handleErrorResponse);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    /**
     * Handles errors in the API request.
     * @param error VolleyError object containing error details.
     */
    private void handleErrorResponse(VolleyError error) {
        if (error.networkResponse != null) {
            String errorMessage = new String(error.networkResponse.data);
            Log.e("Sun_MainActivity", "Error Response: " + errorMessage);
            Toast.makeText(Sun_MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Sun_MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves the provided location to the database.
     * @param latitude  Latitude of the location.
     * @param longitude Longitude of the location.
     */
    private void saveLocationToDatabase(double latitude, double longitude) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "favorite_locations_db").build();
        LocationDao dao = db.locationDao();

        new Thread(() -> {
            dao.insert(new FavoriteLocation(latitude, longitude));
            runOnUiThread(() ->
                    Toast.makeText(Sun_MainActivity.this, R.string.location_saved, Toast.LENGTH_SHORT).show());
        }).start();
    }

    /**
     * Parses and displays the API response.
     * @param response The JSON response from the API.
     */
    private void parseAndDisplayResult(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject results = jsonObject.getJSONObject(getString(R.string.results));
            String sunrise = results.optString("sunrise", getString(R.string.not_available));
            String sunset = results.optString("sunset", getString(R.string.not_available));
            String solarNoon = results.optString("solar_noon", getString(R.string.not_available));

            // Setting the text of each TextView
            binding.textViewSunrise.setText(getString(R.string.template_sunrise, sunrise));
            binding.textViewSunset.setText(getString(R.string.template_sunset, sunset));
            binding.textViewSolarNoon.setText(getString(R.string.template_solar_noon, solarNoon));

            binding.btnSaveToFavorites.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e("Sun_MainActivity", "Error parsing data", e);
            Toast.makeText(this, R.string.parsing_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creates the options menu.
     * @param menu Menu to be created.
     * @return true if menu is created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles options menu item selections.
     * @param item Selected item.
     * @return true if selection handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return ToolbarUtils.handleMenuItem(this, item) || super.onOptionsItemSelected(item);
    }
}
