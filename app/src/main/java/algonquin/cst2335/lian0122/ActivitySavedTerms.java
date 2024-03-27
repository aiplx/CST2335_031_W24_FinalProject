package algonquin.cst2335.lian0122;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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
        SavedTermsAdapter adapter = new SavedTermsAdapter(this, savedTerms);
        binding.savedTermsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.savedTermsRecyclerView.setAdapter(adapter);
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