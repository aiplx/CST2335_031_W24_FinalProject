package algonquin.cst2335.lian0122;// Import statements for necessary Android and Java classes
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import algonquin.cst2335.lian0122.databinding.ActivitySunMainBinding;

public class Sun_MainActivity extends AppCompatActivity {

    // View Binding
    private ActivitySunMainBinding binding;

    // URL for the sunrise and sunset API, formatted for latitude and longitude insertion
    private final String API_URL = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=today&timezone=UTC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflating the view using View Binding and setting it as the content view
        binding = ActivitySunMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        binding.btnSaveToFavorites.setOnClickListener(v -> {
            try {
                double lat = Double.parseDouble(binding.editTextLatitude.getText().toString());
                double lng = Double.parseDouble(binding.editTextLongitude.getText().toString());
                saveLocationToDatabase(lat, lng);
            } catch (NumberFormatException e) {
                Toast.makeText(Sun_MainActivity.this, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting an OnClickListener on 'lookup'
        binding.buttonLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSunriseSunsetLookup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            Intent favoritesIntent = new Intent(this, Sun_FavoritesActivity.class);
            startActivity(favoritesIntent);
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "Created By Oliver Kadvany - 041096826", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.action_return) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help_dialog_title);
        builder.setMessage(R.string.help_dialog_message);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    // Method to handle the lookup action
    private void performSunriseSunsetLookup() {
        // Getting latitude and longitude inputs from EditTexts
        String latitude = binding.editTextLatitude.getText().toString();
        String longitude = binding.editTextLongitude.getText().toString();

        // Check if the input fields are not empty
        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please enter both latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        // Formatting the URL with the entered latitude and longitude
        String url = String.format(API_URL, latitude, longitude);

        // Creating a StringRequest for Volley to perform a network request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        parseAndDisplayResult(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    String errorMessage = new String(error.networkResponse.data);
                    Log.e("Sun_MainActivity", "Error Response: " + errorMessage);
                    Toast.makeText(Sun_MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Sun_MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Adding the request to the Volley request queue to be executed
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void saveLocationToDatabase(double latitude, double longitude) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "favorite_locations_db").build();
        LocationDao dao = db.locationDao();

        new Thread(() -> {
            dao.insert(new FavoriteLocation(latitude, longitude));
            runOnUiThread(() -> Toast.makeText(Sun_MainActivity.this, "Location saved to favorites", Toast.LENGTH_SHORT).show());
        }).start();
    }
    // Method to parse the JSON response and update the UI
    private void parseAndDisplayResult(String response) {
        try {
            // Parsing the JSON response
            JSONObject jsonObject = new JSONObject(response);
            JSONObject results = jsonObject.getJSONObject("results");
            String sunrise = results.getString("sunrise");
            String sunset = results.getString("sunset");

            // Updating the UI with the fetched sunrise and sunset times
            binding.textViewResult.setText("Sunrise: " + sunrise + "\nSunset: " + sunset);
            binding.textViewResult.setVisibility(View.VISIBLE);

            // Turning save button visible
            binding.btnSaveToFavorites.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            // Handling JSON parsing errors
            e.printStackTrace();
            Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
        }
    }

}