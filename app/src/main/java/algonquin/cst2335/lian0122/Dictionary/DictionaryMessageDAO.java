/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Created the DAO of database
 */
package algonquin.cst2335.lian0122.Dictionary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for the DictionaryMessage entity.
 * Provides methods for performing database operations on the dictionary_messages table,
 * such as inserting new entries, retrieving all messages, deleting messages, and finding definitions by search term.
 */
@Dao
public interface DictionaryMessageDAO {

    /**
     * Inserts a new DictionaryMessage into the dictionary_messages table. If the entry already exists, it replaces it.
     *
     * @param m The DictionaryMessage object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DictionaryMessage m);

    /**
     * Retrieves all dictionary messages from the dictionary_messages table.
     *
     * @return A list of all DictionaryMessage entries.
     */
    @Query("SELECT * FROM dictionary_messages")
    List<DictionaryMessage> getAllMessages();

    /**
     * Deletes a specific DictionaryMessage from the dictionary_messages table.
     *
     * @param m The DictionaryMessage object to delete.
     */
    @Delete
    void delete(DictionaryMessage m);

    /**
     * Inserts multiple DictionaryMessage objects into the dictionary_messages table.
     *
     * @param m The list of DictionaryMessage objects to insert.
     */
    @Insert
    void insertAllMessages(List<DictionaryMessage> m);

    /**
     * Retrieves a DictionaryMessage entry from the dictionary_messages table based on the search term.
     * If multiple entries exist for the same search term, returns only the first one.
     *
     * @param searchTerm The search term to query.
     * @return The first DictionaryMessage object matching the search term, or null if not found.
     */
    @Query("SELECT * FROM dictionary_messages WHERE search_term = :searchTerm LIMIT 1")
    DictionaryMessage findDefinitionsBySearchTerm(String searchTerm);
}
