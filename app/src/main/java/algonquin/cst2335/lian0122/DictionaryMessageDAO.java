package algonquin.cst2335.lian0122;

import androidx.lifecycle.LiveData;
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
    LiveData<List<DictionaryMessage>> getAllMessages();

    @Delete
    void delete(DictionaryMessage m);

    @Insert
    void insertAllMessages(List<DictionaryMessage> m);

}
