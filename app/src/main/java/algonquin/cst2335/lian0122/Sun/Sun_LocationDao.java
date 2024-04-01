/**
 * Data Access Object (DAO) for favorite locations.
 * Defines methods for accessing the database.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122.Sun;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for the favorite locations.
 * This interface defines methods for accessing the FavoriteLocation entities in the database.
 */
@Dao
public interface Sun_LocationDao {

    /**
     * Inserts a FavoriteLocation into the database.
     * @param location The FavoriteLocation object to insert.
     */
    @Insert
    void insert(FavoriteLocation location);

    /**
     * Deletes a FavoriteLocation from the database.
     * @param location The FavoriteLocation object to delete.
     */
    @Delete
    void delete(FavoriteLocation location);

    /**
     * Retrieves all favorite locations from the database.
     * @return A list of FavoriteLocation objects.
     */
    @Query("SELECT * FROM favorite_locations")
    List<FavoriteLocation> getAllLocations();
}

