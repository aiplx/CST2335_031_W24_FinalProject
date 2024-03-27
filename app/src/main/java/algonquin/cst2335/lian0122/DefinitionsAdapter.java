package algonquin.cst2335.lian0122;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.lian0122.databinding.ActivityDefinitionBinding;
import algonquin.cst2335.lian0122.databinding.ActivitySavedTermsBinding;

public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {
    private List<DictionaryMessage> definitionsList;

    public DefinitionsAdapter(List<DictionaryMessage> definitionsList) {
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ensure the correct layout is inflated based on view type
        // Assuming you have a layout for definitions
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ActivityDefinitionBinding definitionBinding = ActivityDefinitionBinding.inflate(layoutInflater, parent, false);
        return new DefinitionViewHolder(definitionBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        DictionaryMessage definition = definitionsList.get(position);
        holder.bind(definition);
    }

    @Override
    public int getItemCount() {
        return definitionsList.size();
    }

    @Override
    public int getItemViewType(int position){
        // This method might be necessary if you have different layouts for different types of definitions.
        // For simplicity, assuming all items are of the same type here.
        return DictionaryMessage.TYPE_SEARCH; // Simplification for the purpose of this example
    }
}