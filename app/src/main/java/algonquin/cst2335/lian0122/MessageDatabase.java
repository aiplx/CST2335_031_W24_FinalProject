package algonquin.cst2335.lian0122;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {DictionaryMessage.class},version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract DictionaryMessageDAO dmDAO();

    private static volatile MessageDatabase INSTANCE;

    public static MessageDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MessageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MessageDatabase.class, "dictionary-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}