package algonquin.cst2335.lian0122.music;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Music.class}, version = 3)
public abstract class MusicDatabase  extends RoomDatabase {
    public abstract  MusicDAO musicDAO();
}
