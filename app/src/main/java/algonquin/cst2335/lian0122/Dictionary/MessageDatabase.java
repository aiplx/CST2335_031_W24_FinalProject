/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Configuration of database
 */
package algonquin.cst2335.lian0122.Dictionary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The Room database class for the DictionaryMessage application.
 * This class defines the database configuration and serves as the main access point
 * to the persisted data.
 *
 * @Database annotation specifies the entities and the version of the database.
 * The entities represent the tables in the database, and the version allows for schema migration.
 */
@Database(entities = {DictionaryMessage.class},version = 1)
public abstract class MessageDatabase extends RoomDatabase {

	/**
	 * Provides access to the DictionaryMessage Data Access Object (DAO).
	 * Through the DAO, you can perform CRUD operations on the database.
	 *
	 * @return An implementation of the DictionaryMessageDAO interface.
	 */
	public abstract DictionaryMessageDAO dmDAO();

	/**
	 * The singleton instance of the MessageDatabase.
	 * This instance is volatile to ensure atomic access to the variable across threads.
	 */
	private static volatile MessageDatabase INSTANCE;

	/**
	 * Returns a singleton instance of the MessageDatabase.
	 * This method uses a double-check locking pattern to ensure that the database is
	 * initialized only once and the same instance is returned on every call.
	 *
	 * @param context The Context used to build the database instance. It is recommended
	 *                to use the application context to avoid potential memory leaks.
	 * @return The singleton instance of the MessageDatabase.
	 */
	public static MessageDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			synchronized (MessageDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							MessageDatabase.class, "dictionary-database")
							.fallbackToDestructiveMigration()
							.build();
				}
			}
		}
		return INSTANCE;
	}
}