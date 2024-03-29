/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Defined the definitions' view holder
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import algonquin.cst2335.lian0122.databinding.ActivityDefinitionBinding;
/**
 * ViewHolder class for displaying individual definitions in a RecyclerView.
 * This class is used in conjunction with DefinitionsAdapter to present
 * definition data fetched from a dictionary API or database.
 */
public class DefinitionViewHolder extends RecyclerView.ViewHolder {
	TextView messageText;

	/**
	 * Constructor for DefinitionViewHolder.
	 * Initializes the ViewHolder with the binding to the layout of each item in the RecyclerView.
	 *
	 * @param binding The binding for the activity_definition layout, which contains the TextView
	 *                for displaying a dictionary definition.
	 */
	public DefinitionViewHolder(ActivityDefinitionBinding binding) {
		super(binding.getRoot());
		messageText = binding.definitionMessageText;
	}

	/**
	 * Binds a DictionaryMessage object to this ViewHolder.
	 * This method updates the contents of the ViewHolder to reflect the data of the
	 * DictionaryMessage it is given. Specifically, it sets the text of messageText
	 * TextView to the definitions contained in the DictionaryMessage.
	 *
	 * @param message The DictionaryMessage object containing the definition data
	 *                to be displayed by this ViewHolder.
	 */
	void bind(DictionaryMessage message) {
		messageText.setText(message.getDefinitions());
	}
}