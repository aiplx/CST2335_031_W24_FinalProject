/**
 * Data Access Object (DAO) for favorite locations.
 * Defines methods for accessing the database.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    void insert(FavoriteLocation location);

    @Delete
    void delete(FavoriteLocation location);

    @Query("SELECT * FROM favorite_locations")
    List<FavoriteLocation> getAllLocations();
}

