package algonquin.cst2335.lian0122;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

    // URL for sunrise and sunset API
    private final String API_URL = "https://api.sunrisesunset.io/json?lat=%s&lng=%s&date=today&timezone=UTC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflating the view using View Binding / Setting it as the content view
        binding = ActivitySunMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setting OnClickListener on the 'lookup' button
        binding.buttonLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSunriseSunsetLookup();
            }
        });
    }

    // Method for lookup action
    private void performSunriseSunsetLookup() {
        // Getting latitude and longitude inputs from EditTexts
        String latitude = binding.editTextLatitude.getText().toString();
        String longitude = binding.editTextLongitude.getText().toString();

        // Checking input fields are not empty
        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please enter both latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        // Formatting the URL with the entered latitude and longitude
        String url = String.format(API_URL, latitude, longitude);

        // Creating StringRequest for Volley to perform network request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handling response from server
                        parseAndDisplayResult(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handling errors during the network request
                Toast.makeText(Sun_MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding the request to the Volley request queue to be executed
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Method to parse the JSON response / update the UI
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

        } catch (Exception e) {
            // Handling any JSON parsing errors
            e.printStackTrace();
            Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
        }
    }
}
