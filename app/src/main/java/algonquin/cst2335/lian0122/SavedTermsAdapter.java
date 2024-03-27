package algonquin.cst2335.lian0122;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.lian0122.databinding.ActivitySavedTermsBinding;
import algonquin.cst2335.lian0122.databinding.ItemSavedTermsBinding;

public class SavedTermsAdapter extends RecyclerView.Adapter<SavedTermsViewHolder> {
    private List<DictionaryMessage> savedTerms;
    private Context context;

    public SavedTermsAdapter(Context context, List<DictionaryMessage> savedTerms){
        this.context = context;
        this.savedTerms = savedTerms;
    }

    @NonNull
    @Override
    public SavedTermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemSavedTermsBinding savedTermsBinding = ItemSavedTermsBinding.inflate(layoutInflater, parent, false);
        return new SavedTermsViewHolder(savedTermsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedTermsViewHolder holder, int position) {
        DictionaryMessage term = savedTerms.get(position);
        holder.bind(term);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DictionaryRoom.class); // Intent should be directed to an appropriate activity to show definitions
            intent.putExtra("definitions", term.getDefinitions());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return savedTerms.size();
    }

    @Override
    public int getItemViewType(int position){
        return DictionaryMessage.TYPE_SAVED; // Assuming all items in this adapter are saved terms
    }
//    public static class SavedTermsViewHolder extends RecyclerView.ViewHolder {
//        TextView termTextView;
//
//        SavedTermsViewHolder(ActivitySavedTermsBinding binding) {
//            super(binding.getRoot());
//            termTextView = binding.termTextView;
//        }
//
//        void bind(DictionaryMessage message) {
//            termTextView.setText(message.getSearchTerm());
//        }
//    }
}
