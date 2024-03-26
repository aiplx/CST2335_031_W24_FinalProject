package algonquin.cst2335.lian0122;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DictionaryRoom extends AppCompatActivity {

    private static DictionaryRoom instance;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionary_room);
        Toolbar dictionaryRoomToolbar = findViewById(R.id.dictionaryRoomToolbar);
        setSupportActionBar(dictionaryRoomToolbar);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String lastSearchTerm = sharedPreferences.getString("LastSearchTerm", "");
        EditText searchField = findViewById(R.id.searchField); // Assuming your EditText has this ID
        searchField.setText(lastSearchTerm);
    } // end of onCreate method

    public static synchronized DictionaryRoom getInstance(){
        return instance;
    }// end of DictionaryRoom getInstance method

    // fetch the dictionary content
    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    } // end of RequestQueue method

    public void fetchDefinitions(String word) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // Handle response
                    // Assuming the response is a JSON array, parse it
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject wordObj = response.getJSONObject(i);
                            JSONArray meanings = wordObj.getJSONArray("meanings");
                            // Extract and display definitions...
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    Log.e("Volley", error.toString());
                });

        DictionaryRoom.getInstance().getRequestQueue().add(jsonArrayRequest);
    } // end of fetchDefinitions method

    public void saveLastSearchTerm(String searchTerm) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LastSearchTerm", searchTerm);
        editor.apply();
    }// end of saveLastSearchTerm method

    // add a toolbar to allow user to save the searched term and definition
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dictionary_room_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == R.id.item_1) {
            showSaveMessagesDialog();
            return true;
        } else if (item.getItemId() == R.id.item_2) {
            showToastAboutAPP();
            return true;
        } else if (item.getItemId() == R.id.item_3) {
            showToastAboutAuthor();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    } // end of the onOptionsItemSelected method

    //menu item_1: save the current searched term and definition
    private void showSaveMessagesDialog(){

    }

    //menu item_2: about the app, using AlertDialog
    private void showToastAboutAPP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DictionaryRoom.this);
        builder.setMessage(R.string.app_info_detail)
                .setTitle(R.string.app_info_title)
                .show();
    }

    //menu item_3: about the author, using Toast text
    private void showToastAboutAuthor() {
        Toast.makeText(this, R.string.author_info_detail, Toast.LENGTH_SHORT).show();
    }

}