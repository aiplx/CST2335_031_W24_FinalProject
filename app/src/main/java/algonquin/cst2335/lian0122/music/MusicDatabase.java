/**
 * Name: Jialin Wang
 * ID#: 041041336
 * Section: 031
 * Description: Final project for the course CST2335.
 * Room database class for managing Music entities.
 * Defines the database configuration and provides access to the DAO interface..
 */
package algonquin.cst2335.lian0122.music;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Music.class}, version = 3)
public abstract class MusicDatabase extends RoomDatabase {

    /**
     * Retrieves the MusicDAO interface for interacting with Music entities in the database.
     *
     * @return An instance of the MusicDAO interface.
     */
    public abstract MusicDAO musicDAO();
}
