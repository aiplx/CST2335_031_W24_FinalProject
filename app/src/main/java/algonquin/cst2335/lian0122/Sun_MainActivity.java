package algonquin.cst2335.lian0122;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import algonquin.cst2335.lian0122.databinding.ActivitySunMainBinding;

public class Sun_MainActivity extends AppCompatActivity {

    // URL for the sunrise and sunset API, formatted for latitude and longitude insertion
    private static final String API_URL = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=today&timezone=UTC";

    // View Binding
    private ActivitySunMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySunMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ToolbarUtils.setupToolbar(this, R.id.mainToolbar);

        binding.btnSaveToFavorites.setOnClickListener(v -> {
            try {
                double lat = Double.parseDouble(binding.editTextLatitude.getText().toString());
                double lng = Double.parseDouble(binding.editTextLongitude.getText().toString());
                saveLocationToDatabase(lat, lng);
            } catch (NumberFormatException e) {
                Toast.makeText(Sun_MainActivity.this, R.string.invalid_lat_long, Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonLookup.setOnClickListener(v -> performSunriseSunsetLookup());
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

    private void performSunriseSunsetLookup() {
        String latitude = binding.editTextLatitude.getText().toString();
        String longitude = binding.editTextLongitude.getText().toString();

        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_both_lat_long, Toast.LENGTH_SHORT).show();
            return;
        }

        String url = String.format(API_URL, latitude, longitude);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                this::parseAndDisplayResult, this::handleErrorResponse);

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void handleErrorResponse(VolleyError error) {
        if (error.networkResponse != null) {
            String errorMessage = new String(error.networkResponse.data);
            Log.e("Sun_MainActivity", "Error Response: " + errorMessage);
            Toast.makeText(Sun_MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Sun_MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }

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

    private void parseAndDisplayResult(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject results = jsonObject.getJSONObject("results");
            String sunrise = results.getString("sunrise");
            String sunset = results.getString("sunset");

            binding.textViewResult.setText(getString(R.string.sunrise_sunset_result, sunrise, sunset));
            binding.textViewResult.setVisibility(View.VISIBLE);
            binding.btnSaveToFavorites.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e("Sun_MainActivity", "Error parsing data", e);
            Toast.makeText(this, R.string.parsing_error, Toast.LENGTH_SHORT).show();
        }
    }
}
