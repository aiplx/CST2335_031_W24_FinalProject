/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: The launch page of this dictionary APP
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.li000543.Recipe.RecipeMain;
import algonquin.cst2335.lian0122.R;
import algonquin.cst2335.lian0122.Sun.Sun_MainActivity;
import algonquin.cst2335.lian0122.databinding.ActivityDictionaryRoomBinding;
import algonquin.cst2335.lian0122.music.MusicActivity;


/**
 * The main activity class for the DictionaryRoom application.
 * This class is responsible for initializing the UI components, setting up the RecyclerView
 * for displaying definitions, and handling user interactions such as searching for definitions
 * and navigating to saved terms.
 */
public class DictionaryRoom extends AppCompatActivity {
	private Dict_DefinitionsAdapter myAdapter; // use a specific adapter type
	private ActivityDictionaryRoomBinding binding;
	private List<DictionaryMessage> dictionaryMessages = new ArrayList<>();
	private static DictionaryRoom instance;
	private RequestQueue requestQueue;

	/**
	 * Initializes the activity, setting up the toolbar, RecyclerView, and button click listeners.
	 * Restores saved state if available, loads the last searched term, and initializes the Volley request queue.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityDictionaryRoomBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		setSupportActionBar(binding.dictionaryRoomToolbar);
		// Assuming the initialization of requestQueue here instead of using a static instance
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		setupRecyclerView();

		if (savedInstanceState != null && savedInstanceState.containsKey("definitions")) {
			// Restore the list of DictionaryMessage objects from JSON String
			Type listType = new TypeToken<ArrayList<DictionaryMessage>>() {}.getType();
			dictionaryMessages = new Gson().fromJson(savedInstanceState.getString("definitions"), listType);
			myAdapter = new Dict_DefinitionsAdapter(dictionaryMessages);
			binding.recycleView.setAdapter(myAdapter);
		}

		// Load last search term
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.dict_sharedPreferences_AppPreferences), MODE_PRIVATE);
		String lastSearchTerm = sharedPreferences.getString(getString(R.string.dict_sharedPreferences_LastSearchTerm), "");
		binding.searchField.setText(lastSearchTerm);

		binding.searchButton.setOnClickListener(cl -> {
			String searchTerm = binding.searchField.getText().toString().trim();
			if (!searchTerm.isEmpty()) {
				fetchDefinitions(searchTerm,this::updateDefinitionsList);
				saveLastSearchTerm(searchTerm);
			}
		}); // end of Search button

		binding.savedTermsButton.setOnClickListener(cl ->{
			Intent intent = new Intent(DictionaryRoom.this, Dict_ActivitySavedTerms.class);
			startActivity(intent);
		});// end of SavedTerms button

	} // end of onCreate method

	/**
	 * Saves the current state of dictionaryMessages before the activity is destroyed, for example during a rotation.
	 * @param outState Bundle where the saved state is placed.
	 */
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		// Convert the list of DictionaryMessage objects to JSON String
		String definitionsJson = new Gson().toJson(dictionaryMessages);
		outState.putString("definitions", definitionsJson);
	} // end of onSaveInstanceState method

	/**
	 * Sets up the RecyclerView with the DefinitionsAdapter and assigns it to the RecyclerView in the layout.
	 */
	private void setupRecyclerView() {
		myAdapter = new Dict_DefinitionsAdapter(dictionaryMessages);
		binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
		binding.recycleView.setAdapter(myAdapter);
	}

	/**
	 * Updates the list of definitions displayed in the RecyclerView.
	 * @param definitions The new list of DictionaryMessage objects containing the definitions to be displayed.
	 */
	@SuppressLint("NotifyDataSetChanged")
	private void updateDefinitionsList(List<DictionaryMessage> definitions) {
		dictionaryMessages.clear();
		dictionaryMessages.addAll(definitions);
		myAdapter.notifyDataSetChanged(); // Notify the adapter about data change
	} // end of updateDefinitionsList method

	/**
	 * Fetches the definitions for a given searchTerm from the dictionary API.
	 * Updates the UI with the fetched definitions upon successful retrieval.
	 * @param searchTerm The term for which definitions are to be fetched.
	 * @param onDefinitionsFetched A Consumer functional interface to handle the list of fetched definitions.
	 */
	public void fetchDefinitions(String searchTerm, Consumer<List<DictionaryMessage>> onDefinitionsFetched) {
		String url = getString(R.string.dict_api_url) + searchTerm;

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
				response -> {
					List<DictionaryMessage> definitions = new ArrayList<>();
					StringBuilder definitionsConcatenated = new StringBuilder();

					try {
						// Log the response for debugging
						Log.d("fetchDefinitions", "Response: " + response.toString());
						// Parsing response and adding to definitions list
						for (int i = 0; i < response.length(); i++) {
							JSONObject wordObj = response.getJSONObject(i);
							JSONArray meanings = wordObj.getJSONArray(getString(R.string.dict_meaningsArray));
							// Extract and display definitions
							for (int j = 0; j < meanings.length(); j++) {
								JSONObject meaningObj = meanings.getJSONObject(j);
								JSONArray definitionsArray = meaningObj.getJSONArray(getString(R.string.dict_definitionsArray));
								for (int k = 0; k < definitionsArray.length(); k++) {
									JSONObject definitionObj = definitionsArray.getJSONObject(k);
									String definitionText = definitionObj.getString(getString(R.string.dict_definitionObj));
									definitionsConcatenated.append(definitionText).append("\n\n");
								}
							}
						}
						//                         Create a single DictionaryMessage object if definitions were found
						if (definitionsConcatenated.length() > 0) {
							DictionaryMessage dictionaryMessage = new DictionaryMessage(searchTerm, definitionsConcatenated.toString(), DictionaryMessage.TYPE_SEARCH);
							definitions.add(dictionaryMessage);
						}
						onDefinitionsFetched.accept(definitions);
						// Update the UI with the fetched definitions
						runOnUiThread(() -> {
							dictionaryMessages.clear();
							dictionaryMessages.addAll(definitions);
							myAdapter.notifyDataSetChanged();
						});
					} catch (JSONException e) {
						Log.e("fetchDefinitions", "Json parsing error: " + e.getMessage());
					}
				},
				error -> Log.e("fetchDefinitions", "Volley error: " + error.toString()));
		requestQueue.add(jsonArrayRequest);
	}// end of fetchDefinitions method

	/**
	 * Saves the last searched term in SharedPreferences for later retrieval.
	 * @param searchTerm The term that was last searched.
	 */
	public void saveLastSearchTerm(String searchTerm) {
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.dict_sharedPreferences_AppPreferences), MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(getString(R.string.dict_sharedPreferences_LastSearchTerm), searchTerm);
		editor.apply();
	}// end of saveLastSearchTerm method

	/**
	 * Inflates the menu for the activity and sets up menu items for additional functionalities.
	 * @param menu The menu in which we can place our items.
	 * @return true for the menu to be displayed
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dictionary_room_menu, menu);
		return true;
	}

	/**
	 * Handles click events on the menu items.
	 * Includes saving the current search term and definition, showing app information, and showing author information.
	 * @param item The menu item that was selected.
	 * @return false to allow normal menu processing to proceed, true to consume it here.
	 */
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
		}else if (item.getItemId() == R.id.item_sun) {
			Intent sunPage = new Intent(DictionaryRoom.this, Sun_MainActivity.class);
			startActivity(sunPage);
			return true;

		} else if (item.getItemId() == R.id.item_recipe) {
			Intent recipePage = new Intent(DictionaryRoom.this, RecipeMain.class);
			startActivity(recipePage);
			return true;
		}else if (item.getItemId() == R.id.item_music) {
			            Intent musicPage = new Intent(DictionaryRoom.this, MusicActivity.class);
			            startActivity(musicPage);
			            return true;}
		else {
			return super.onOptionsItemSelected(item);
		}
	} // end of the onOptionsItemSelected method

	/**
	 * menu item_1: Displays a dialog allowing the user to save the current searched term and its definition.
	 * Checks if the entry already exists in the database before attempting to insert.
	 */
	private void showSaveMessagesDialog() {
		String searchTerm = binding.searchField.getText().toString();

		fetchDefinitions(searchTerm, definitions -> {
			// Convert definitions to a JSON string
			String definitionsString = convertDefinitionsToString(definitions);

			// Prepare the dictionary message for insertion
			DictionaryMessage newEntry = new DictionaryMessage(searchTerm, definitionsString,DictionaryMessage.TYPE_SEARCH);
			DictionaryMessageDAO dao = MessageDatabase.getInstance(getApplicationContext()).dmDAO();

			new Thread(() -> {
				// Check if the entry already exists in the database
				DictionaryMessage existingEntry = dao.findDefinitionsBySearchTerm(searchTerm);
				if (existingEntry == null) {
					// If it doesn't exist, insert the new entry
					dao.insert(newEntry);
					runOnUiThread(() -> Toast.makeText(this, R.string.dict_save_success, Toast.LENGTH_SHORT).show());
				} else {
					// If it exists, show a toast message
					runOnUiThread(() -> Toast.makeText(this, R.string.dict_save_again_message, Toast.LENGTH_SHORT).show());
				}
			}).start();
		});
	}// end of showSaveMessagesDialog method

	/**
	 * Converts a list of DictionaryMessage objects to a JSON string.
	 * @param dictionaryMessages The list of DictionaryMessage objects to be converted.
	 * @return The JSON string representation of the list.
	 */
	private String convertDefinitionsToString(List<DictionaryMessage> dictionaryMessages) {
		Gson gson = new Gson();
		return gson.toJson(dictionaryMessages);
	}

	/**
	 * menu item_2: Shows an AlertDialog with information about the application.
	 */
	private void showToastAboutAPP() {
		AlertDialog.Builder builder = new AlertDialog.Builder(DictionaryRoom.this);
		builder.setMessage(R.string.dict_app_info_detail)
		.setTitle(R.string.dict_app_info_title)
		.show();
	}

	/**
	 * menu item_3: Shows a Toast message with information about the author.
	 */
	private void showToastAboutAuthor() {
		Toast.makeText(this, R.string.dict_author_info_detail, Toast.LENGTH_SHORT).show();
	}
}