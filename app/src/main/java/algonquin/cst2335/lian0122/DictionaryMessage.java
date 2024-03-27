package algonquin.cst2335.lian0122;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dictionary_messages")
public class DictionaryMessage {
    public static final int TYPE_SEARCH = 1;
    public static final int TYPE_SAVED = 2;
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "search_term")
    public String searchTerm;

    @ColumnInfo(name = "definitions")
    public String definitions; // Stored as a serialized JSON String

    @ColumnInfo(name = "MessageType")
    protected int buttonType; // Use this to store message type (sent or received)

    // Constructor and getters
    public DictionaryMessage(String searchTerm, String definitions, int buttonType) {
        this.searchTerm = searchTerm;
        this.definitions = definitions;
        this.buttonType = buttonType;
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

    public int getButtonType(){return buttonType;}

    @NonNull
    @Override
    public String toString(){return definitions;}
}