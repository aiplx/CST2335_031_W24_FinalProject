/**
 * FavoriteLocation entity class for the Room database.
 * This class represents a table for storing favorite locations with latitude, longitude, and solar noon.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_locations")
public class FavoriteLocation {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double latitude;
    public double longitude;

    public FavoriteLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
