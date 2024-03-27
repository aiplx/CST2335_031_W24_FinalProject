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

