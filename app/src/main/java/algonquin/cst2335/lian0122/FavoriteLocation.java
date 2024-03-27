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
