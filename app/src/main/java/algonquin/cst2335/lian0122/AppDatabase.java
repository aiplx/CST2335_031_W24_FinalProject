package algonquin.cst2335.lian0122;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteLocation.class}, version = 1, exportSchema = false)

/**
 * Represents the application's database.
 * Extends RoomDatabase to leverage Room's database capabilities.
 * Defines the database schema and version.
 */
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Provides access to LocationDao for database operations.
     * @return Instance of LocationDao.
     */
    public abstract LocationDao locationDao();
}

