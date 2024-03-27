package algonquin.cst2335.lian0122.Dictionary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DictionaryMessageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DictionaryMessage m);

    @Query("SELECT * FROM dictionary_messages")
    List<DictionaryMessage> getAllMessages();

    @Delete
    void delete(DictionaryMessage m);

    @Insert
    void insertAllMessages(List<DictionaryMessage> m);

    @Query("SELECT * FROM dictionary_messages WHERE search_term = :searchTerm LIMIT 1")
    DictionaryMessage findDefinitionsBySearchTerm(String searchTerm);

}
