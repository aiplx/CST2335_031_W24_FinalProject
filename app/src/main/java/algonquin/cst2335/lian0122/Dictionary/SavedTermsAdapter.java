package algonquin.cst2335.lian0122.Dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.lian0122.R;

public class SavedTermsAdapter extends RecyclerView.Adapter<SavedTermsAdapter.SavedTermsViewHolder> {
    private List<DictionaryMessage> savedTerms;
    private Context context;
    private LayoutInflater layoutInflater;

    // Define an interface for click events
    public interface OnItemClickListener {
        void onItemClick(DictionaryMessage message);
    }

    private OnItemClickListener listener;
    public SavedTermsAdapter(Context context, List<DictionaryMessage> savedTerms, OnItemClickListener listener) {
        this.context = context;
        this.savedTerms = savedTerms;
        this.layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedTermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        ItemSavedTermsBinding savedTermsBinding = ItemSavedTermsBinding.inflate(layoutInflater, parent, false);
//        return new SavedTermsViewHolder(savedTermsBinding);
        View itemView = layoutInflater.inflate(R.layout.item_saved_terms, parent, false);
        return new SavedTermsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedTermsViewHolder holder, int position) {
        DictionaryMessage term = savedTerms.get(position);
        holder.bind(term);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(term));
    }

    @Override
    public int getItemCount() {
        return savedTerms.size();
    }

    @Override
    public int getItemViewType(int position){
        return DictionaryMessage.TYPE_SAVED; // Assuming all items in this adapter are saved terms
    }
    static class SavedTermsViewHolder extends RecyclerView.ViewHolder {
        TextView termTextView;

        SavedTermsViewHolder(View itemView) {
            super(itemView);
            termTextView = itemView.findViewById(R.id.termTextView);
        }

        void bind(DictionaryMessage message) {
            termTextView.setText(message.getSearchTerm());
        }
    }
}
