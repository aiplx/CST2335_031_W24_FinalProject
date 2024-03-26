package algonquin.cst2335.lian0122;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dictionary_messages")
public class DictionaryMessage {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "search_term")
    public String searchTerm;

    @ColumnInfo(name = "definitions")
    public String definitions; // Stored as a serialized JSON String

    // Constructor and getters
    public DictionaryMessage(String searchTerm, String definitions) {
        this.searchTerm = searchTerm;
        this.definitions = definitions;
    }

    public int getId() {
        return id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getDefinitions() {
        return definitions;
    }

    @NonNull
    @Override
    public String toString(){return definitions;}
}