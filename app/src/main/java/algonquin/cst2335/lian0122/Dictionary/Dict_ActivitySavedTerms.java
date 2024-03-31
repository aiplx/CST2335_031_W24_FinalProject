/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: The activity of the saved terms
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import algonquin.cst2335.li000543.R;
import algonquin.cst2335.li000543.databinding.ActivitySavedTermsBinding;


/**
 * Activity to display saved terms from the Room Database and their definitions.
 * Provides functionality to view, delete, and undo the deletion of terms and definitions.
 */
public class Dict_ActivitySavedTerms extends AppCompatActivity {
	private ActivitySavedTermsBinding binding;

	/**
	 * Initializes the activity, sets up the content view, and loads saved terms from the database.
	 * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise, it is null.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		loadSavedTerms();
	}

	/**
	 * Sets up the RecyclerView with saved terms using {@link Dict_SavedTermsAdapter}.
	 * @param savedTerms List of {@link DictionaryMessage} containing saved terms and definitions to be displayed.
	 */
	private void setupRecyclerView(List<DictionaryMessage> savedTerms) {
		Dict_SavedTermsAdapter adapter = new Dict_SavedTermsAdapter(this, savedTerms, this::showDefinitionPopup);
		binding.savedTermsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		binding.savedTermsRecyclerView.setAdapter(adapter);
	}

	/**
	 * Shows a popup dialog with the definition of a saved term. Provides options to close or delete the term.
	 * @param message {@link DictionaryMessage} containing the term and its definition to be shown.
	 */
	private void showDefinitionPopup(DictionaryMessage message) {
		String formattedDefinitions = formatDefinitions(message.getDefinitions());

		new AlertDialog.Builder(this)
		.setTitle(message.getSearchTerm())
		.setMessage(formattedDefinitions)
		.setPositiveButton(android.R.string.ok, null)
		.setNegativeButton(R.string.dict_delete_alertDialog, (dialogInterface, i) -> deleteDefinition(message))
		.show();
	}

	/**
	 * Formats a JSON string of definitions to a more human-readable format.
	 * @param definitionsJson JSON string containing the definitions.
	 * @return Formatted string of definitions.
	 */
	private String formatDefinitions(String definitionsJson) {
		try {
			// Parse the JSON array from the definitions string
			JSONArray jsonArray = new JSONArray(definitionsJson);
			StringBuilder formattedText = new StringBuilder();

			// Iterate through each item in the array (though based on your structure, you might have just one)
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				// Extract the definitions
				String definitions = jsonObject.getString("definitions");

				// Append the formatted definition to the StringBuilder
				formattedText
				.append("\n")
				.append(getString(R.string.dict_definitions))
				.append("\n\n")
				.append(definitions);
			}

			return formattedText.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return getString(R.string.dict_error_format_definitions);
		}
	}

	/**
	 * Deletes a definition from the Room Database and provides an undo option via a Snackbar.
	 * @param message {@link DictionaryMessage} containing the term and definition to be deleted.
	 */
	private void deleteDefinition(DictionaryMessage message) {
		DictionaryMessageDAO dao = MessageDatabase.getInstance(getApplicationContext()).dmDAO();
		new Thread(() -> {
			dao.delete(message);

			// Remove the item from the current list to immediately reflect the change in UI
			runOnUiThread(() -> {
				// Show a SnackBar with Undo option
				Snackbar.make(binding.getRoot(), getString(R.string.dict_definition_deletion_message) + message.getSearchTerm(), Snackbar.LENGTH_LONG)
				.setAction(R.string.dict_undo_message, view -> undoDelete(message, dao))
				.show();
				loadSavedTerms(); // Reload to reflect deletion, consider optimizing to avoid full reload
			});
		}).start();
	}

	/**
	 * Restores a previously deleted definition back to the Room Database.
	 * @param message {@link DictionaryMessage} to be restored.
	 * @param dao {@link DictionaryMessageDAO} for accessing the Room Database.
	 */
	private void undoDelete(DictionaryMessage message, DictionaryMessageDAO dao) {
		new Thread(() -> {
			dao.insert(message); // Re-insert the deleted definitions
			runOnUiThread(this::loadSavedTerms); // Reload saved terms to reflect the undo action
		}).start();
	}

	/**
	 * Loads saved terms from the Room Database and updates the RecyclerView.
	 */
	private void loadSavedTerms() {
		DictionaryMessageDAO dao = MessageDatabase.getInstance(getApplicationContext()).dmDAO();
		new Thread(() -> {
			List<DictionaryMessage> savedTerms = dao.getAllMessages();
			runOnUiThread(() -> setupRecyclerView(savedTerms));
		}).start();
	}
}