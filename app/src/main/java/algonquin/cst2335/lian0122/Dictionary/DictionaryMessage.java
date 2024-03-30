/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Created the table and entities for Room Database
 */
package algonquin.cst2335.lian0122.Dictionary;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a message in the dictionary database.
 * This class is used to model the data for a dictionary entry including the search term,
 * its definitions, and the type of entry (search or saved).
 */
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
    protected int buttonType; // Use this to store message type (search or save)

    /**
     * Constructs a new DictionaryMessage with the specified search term, definitions, and message type.
     *
     * @param searchTerm The search term of the dictionary entry.
     * @param definitions Serialized JSON string containing the definitions for the search term.
     * @param buttonType Indicates the type of message (search result or saved entry).
     */
    public DictionaryMessage(String searchTerm, String definitions, int buttonType) {
        this.searchTerm = searchTerm;
        this.definitions = definitions;
        this.buttonType = buttonType;
    }

    /**
     * Returns the unique identifier of the dictionary entry.
     * @return The id of the entry.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the search term associated with the dictionary entry.
     * @return The search term.
     */
    public String getSearchTerm() {
        return searchTerm;
    }

    /**
     * Returns the serialized JSON string of definitions for the search term.
     * @return The definitions in JSON format.
     */
    public String getDefinitions() {
        return definitions;
    }

    /**
     * Returns the type of message, indicating whether it is a search result or a saved entry.
     * @return The type of message (TYPE_SEARCH or TYPE_SAVED).
     */
    public int getButtonType(){return buttonType;}

    /**
     * Returns a string representation of the dictionary entry's definitions.
     * @return The definitions string.
     */
    @NonNull
    @Override
    public String toString(){return definitions;}
}