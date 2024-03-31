/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Defined the savedTerms' adapter
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.li000543.R;


/**
 * Adapter for a RecyclerView that displays saved terms from the dictionary.
 * This adapter is responsible for providing views that represent each saved term in a list.
 */
public class Dict_SavedTermsAdapter extends RecyclerView.Adapter<Dict_SavedTermsAdapter.SavedTermsViewHolder> {
	private List<DictionaryMessage> savedTerms;
	private Context context;
	private LayoutInflater layoutInflater;

	/**
	 * Interface definition for a callback to be invoked when an item in the RecyclerView is clicked.
	 */
	public interface OnItemClickListener {
		void onItemClick(DictionaryMessage message);
	}

	/**
	 * The listener that receives notifications when an item is clicked.
	 */
	private OnItemClickListener listener;

	/**
	 * Constructor for SavedTermsAdapter.
	 *
	 * @param context The current context.
	 * @param savedTerms The list of saved dictionary messages.
	 * @param listener The callback that will run when an item is clicked.
	 */
	public Dict_SavedTermsAdapter(Context context, List<DictionaryMessage> savedTerms, OnItemClickListener listener) {
		this.context = context;
		this.savedTerms = savedTerms;
		this.layoutInflater = LayoutInflater.from(context);
		this.listener = listener;
	}

	/**
	 * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
	 *
	 * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
	 * @param viewType The view type of the new View.
	 * @return A new ViewHolder that holds a View of the given view type.
	 */
	@NonNull
	@Override
	public SavedTermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		//        LayoutInflater layoutInflater = LayoutInflater.from(context);
		//        ItemSavedTermsBinding savedTermsBinding = ItemSavedTermsBinding.inflate(layoutInflater, parent, false);
		//        return new SavedTermsViewHolder(savedTermsBinding);
		View itemView = layoutInflater.inflate(R.layout.item_saved_terms, parent, false);
		return new SavedTermsViewHolder(itemView);
	}

	/**
	 * Called by RecyclerView to display the data at the specified position.
	 *
	 * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
	 * @param position The position of the item within the adapter's data set.
	 */
	@Override
	public void onBindViewHolder(@NonNull SavedTermsViewHolder holder, int position) {
		DictionaryMessage term = savedTerms.get(position);
		holder.bind(term);
		holder.itemView.setOnClickListener(v -> listener.onItemClick(term));
	}

	/**
	 * Returns the total number of items in the data set held by the adapter.
	 *
	 * @return The total number of items in this adapter.
	 */
	@Override
	public int getItemCount() {
		return savedTerms.size();
	}

	/**
	 * Returns the view type of the item at position for the purposes of view recycling.
	 *
	 * @param position The position of the item within the adapter's data set.
	 * @return The view type (DictionaryMessage.TYPE_SAVED) of the item at position.
	 */
	@Override
	public int getItemViewType(int position){
		return DictionaryMessage.TYPE_SAVED; // Assuming all items in this adapter are saved terms
	}

	/**
	 * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
	 */
	static class SavedTermsViewHolder extends RecyclerView.ViewHolder {
		TextView termTextView;

		/**
		 * Constructor for the ViewHolder, called in onCreateViewHolder.
		 *
		 * @param itemView The root view of the saved_terms_item.xml layout file.
		 */
		SavedTermsViewHolder(View itemView) {
			super(itemView);
			termTextView = itemView.findViewById(R.id.termTextView);
		}

		/**
		 * Binds the DictionaryMessage object data to the TextView.
		 *
		 * @param message The DictionaryMessage object.
		 */
		void bind(DictionaryMessage message) {
			termTextView.setText(message.getSearchTerm());
		}
	}
}
