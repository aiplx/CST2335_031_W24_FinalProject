/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Defined the savedTerms' view holder
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.li000543.databinding.ItemSavedTermsBinding;


/**
 * ViewHolder for displaying a saved term within a RecyclerView in the SavedTermsAdapter.
 */
public class Dict_SavedTermsViewHolder extends RecyclerView.ViewHolder {
	private final ItemSavedTermsBinding binding;
	TextView termTextView;

	/**
	 * Constructor that binds the SavedTerms layout and initializes the TextView.
	 *
	 * @param savedTermsBinding Binding instance of the item layout.
	 */
	public Dict_SavedTermsViewHolder(ItemSavedTermsBinding savedTermsBinding) {
		super(savedTermsBinding.getRoot());
		this.binding = savedTermsBinding;
		termTextView = this.binding.termTextView;
	}

	/**
	 * Binds a DictionaryMessage to the ViewHolder, setting the term's text to the TextView.
	 *
	 * @param message The DictionaryMessage object that contains the data to be displayed by this ViewHolder.
	 */
	void bind(DictionaryMessage message) {
		termTextView.setText(message.getSearchTerm());
	}
}