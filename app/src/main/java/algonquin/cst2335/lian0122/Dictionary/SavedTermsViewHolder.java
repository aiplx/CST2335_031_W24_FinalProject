package algonquin.cst2335.lian0122.Dictionary;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import algonquin.cst2335.lian0122.databinding.ItemSavedTermsBinding;

public class SavedTermsViewHolder extends RecyclerView.ViewHolder {
    private final ItemSavedTermsBinding binding;
    TextView termTextView;

    public SavedTermsViewHolder(ItemSavedTermsBinding savedTermsBinding) {
        super(savedTermsBinding.getRoot());
        this.binding = savedTermsBinding;
        termTextView = this.binding.termTextView;
    }

    void bind(DictionaryMessage message) {
        termTextView.setText(message.getSearchTerm());
    }
}