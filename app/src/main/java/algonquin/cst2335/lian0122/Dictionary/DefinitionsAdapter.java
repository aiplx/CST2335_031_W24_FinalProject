/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Defined the definitions' adapter
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.lian0122.databinding.ActivityDefinitionBinding;
/**
 * Adapter class for a RecyclerView in the DictionaryRoom activity.
 * This adapter is responsible for handling a list of DictionaryMessage objects,
 * each representing a definition to be displayed in the RecyclerView.
 */
public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {
	private List<DictionaryMessage> definitionsList;

	/**
	 * Constructs a DefinitionsAdapter with a specified list of DictionaryMessage objects.
	 * @param definitionsList List of DictionaryMessage objects to be displayed by the adapter.
	 */
	public DefinitionsAdapter(List<DictionaryMessage> definitionsList) {
		this.definitionsList = definitionsList;
	}

	/**
	 * Inflates the layout for individual list items in the RecyclerView.
	 * This method is called when the RecyclerView needs a new DefinitionViewHolder
	 * to represent an item.
	 * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
	 * @param viewType The view type of the new View.
	 * @return A new DefinitionViewHolder that holds a View of the given view type.
	 */
	@NonNull
	@Override
	public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// the layout is inflated
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ActivityDefinitionBinding definitionBinding = ActivityDefinitionBinding.inflate(layoutInflater, parent, false);
		return new DefinitionViewHolder(definitionBinding);
	}

	/**
	 * Binds a DictionaryMessage object to a DefinitionViewHolder at a specified position.
	 * This method is used to populate the ViewHolder with the content of the item at the given position.
	 * @param holder The DefinitionViewHolder which should be updated to represent the contents of the item at the given position in the data set.
	 * @param position The position of the item within the adapter's data set.
	 */
	@Override
	public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
		DictionaryMessage definition = definitionsList.get(position);
		holder.bind(definition);
	}

	/**
	 * Returns the total number of items in the data set held by the adapter.
	 * @return The total number of items in this adapter.
	 */
	@Override
	public int getItemCount() {
		return definitionsList.size();
	}

	/**
	 * Returns the view type of the item at the given position for the purposes of view recycling.
	 * @param position The position of the item within the adapter's data set.
	 * @return The view type (integer) of the item at the given position.
	 */
	@Override
	public int getItemViewType(int position){
		// all items are of the same type here.
		return DictionaryMessage.TYPE_SEARCH;
	}
}