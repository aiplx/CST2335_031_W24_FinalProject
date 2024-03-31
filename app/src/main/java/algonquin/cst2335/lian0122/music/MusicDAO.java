/**
 * Name: Jialin Wang
 * ID#: 041041336
 * Section: 031
 * Description: Final project for the course CST2335.
 * The Data Access Object (DAO) interface for Music entities.
 * Defines methods to interact with the Music table in the database.
 */

package algonquin.cst2335.lian0122.music;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MusicDAO {

    /**
     * Inserts a Music entity into the database.
     *
     * @param m The Music object to insert.
     * @return The row ID of the newly inserted Music entity.
     */
    @Insert
    long insertMusic(Music m);

    /**
     * Retrieves all Music entities from the database.
     *
     * @return A list containing all Music entities stored in the database.
     */
    @Query("SELECT * FROM Music")
    List<Music> getALLMusics();

    /**
     * Updates a Music entity in the database.
     *
     * @param m The Music object to update.
     */
    @Update
    void updateMusic(Music m);

    /**
     * Deletes a Music entity from the database.
     *
     * @param m The Music object to delete.
     */
    @Delete
    void deleteMusic(Music m);
}
