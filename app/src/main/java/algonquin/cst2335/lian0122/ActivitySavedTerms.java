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

import java.util.ArrayList;
import java.util.List;

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
        new AlertDialog.Builder(this)
                .setTitle(message.getSearchTerm())
                .setMessage(message.getDefinitions())
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton("Delete", (dialogInterface, i) -> deleteDefinition(message))
                .show();
    }

    private void deleteDefinition(DictionaryMessage message) {
        DictionaryMessageDAO dao = MessageDatabase.getInstance(getApplicationContext()).dmDAO();
        new Thread(() -> {
            dao.delete(message);
            // Reload your saved terms after deletion
            loadSavedTerms();
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