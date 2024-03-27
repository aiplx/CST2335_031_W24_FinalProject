package algonquin.cst2335.lian0122;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import algonquin.cst2335.lian0122.databinding.ActivitySavedTermsBinding;

public class ActivitySavedTerms extends AppCompatActivity {
    private ActivitySavedTermsBinding binding;
//    private SavedTermsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadSavedTerms();
    }

    private void setupRecyclerView(List<DictionaryMessage> savedTerms) {
        // This block will be executed when an item is clicked.
        // You can show a dialog with the definition or start a new activity.
        SavedTermsAdapter adapter = new SavedTermsAdapter(this, savedTerms, this::showDefinitionPopup);
        binding.savedTermsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.savedTermsRecyclerView.setAdapter(adapter);
    }
    private void showDefinitionPopup(DictionaryMessage message) {
        String formattedDefinitions = formatDefinitions(message.getDefinitions());

        new AlertDialog.Builder(this)
                .setTitle(message.getSearchTerm())
                .setMessage(formattedDefinitions)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton("Delete", (dialogInterface, i) -> deleteDefinition(message))
                .show();
    }

    /**
     * Formats the JSON string of definitions to a more readable format.
     *
     * @param definitionsJson The JSON string containing definitions.
     * @return A formatted string suitable for display.
     */
    private String formatDefinitions(String definitionsJson) {
        try {
            // Parse the JSON array from the definitions string
            JSONArray jsonArray = new JSONArray(definitionsJson);
            StringBuilder formattedText = new StringBuilder();

            // Iterate through each item in the array (though based on your structure, you might have just one)
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract the searchTerm and definitions
//                String searchTerm = jsonObject.getString("searchTerm");
                String definitions = jsonObject.getString("definitions");

                // Append the formatted definition to the StringBuilder
                formattedText
                        .append("\n\nDefinitions:\n")
                        .append(definitions);
            }

            return formattedText.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error formatting definitions.";
        }
    }


    private void deleteDefinition(DictionaryMessage message) {
        DictionaryMessageDAO dao = MessageDatabase.getInstance(getApplicationContext()).dmDAO();
        new Thread(() -> {
            dao.delete(message);

            // Temporarily store the deleted item for undo action
            DictionaryMessage deletedMessage = message;

            // Remove the item from the current list to immediately reflect the change in UI
            // Assuming savedTerms is accessible; otherwise, retrieve it again or adjust logic
            runOnUiThread(() -> {
                // Show a SnackBar with Undo option
                Snackbar.make(binding.getRoot(), "Definition deleted for word: " + message.getSearchTerm(), Snackbar.LENGTH_LONG)
                        .setAction("UNDO", view -> undoDelete(deletedMessage, dao))
                        .show();
                loadSavedTerms(); // Reload to reflect deletion, consider optimizing to avoid full reload
            });
        }).start();
    }

    private void undoDelete(DictionaryMessage message, DictionaryMessageDAO dao) {
        new Thread(() -> {
            dao.insert(message); // Re-insert the deleted message
            runOnUiThread(this::loadSavedTerms); // Reload saved terms to reflect the undo action
        }).start();
    }

    // Load saved terms from Room database
    private void loadSavedTerms() {
        DictionaryMessageDAO dao = MessageDatabase.getInstance(getApplicationContext()).dmDAO();
        new Thread(() -> {
            List<DictionaryMessage> savedTerms = dao.getAllMessages();
            runOnUiThread(() -> setupRecyclerView(savedTerms));
        }).start();
    }
}