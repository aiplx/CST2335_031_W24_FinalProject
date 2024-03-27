package algonquin.cst2335.lian0122.Dictionary;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import algonquin.cst2335.lian0122.databinding.ActivityDefinitionBinding;

public class DefinitionViewHolder extends RecyclerView.ViewHolder {
    TextView messageText;
    public DefinitionViewHolder(ActivityDefinitionBinding binding) {
        super(binding.getRoot());
        messageText = binding.definitionMessageText;
    }
    void bind(DictionaryMessage message) {
        messageText.setText(message.getDefinitions());
    }
}