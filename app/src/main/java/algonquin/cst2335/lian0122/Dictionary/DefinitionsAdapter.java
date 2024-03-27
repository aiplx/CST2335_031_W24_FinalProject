package algonquin.cst2335.lian0122.Dictionary;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.lian0122.databinding.ActivityDefinitionBinding;

public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {
    private List<DictionaryMessage> definitionsList;

    public DefinitionsAdapter(List<DictionaryMessage> definitionsList) {
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // the layout is inflated
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
        // all items are of the same type here.
        return DictionaryMessage.TYPE_SEARCH;
    }
}