package algonquin.cst2335.lian0122.music;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MusicDAO {

    @Insert
    long insertMusic(Music m);

    @Query("Select * from Music")
    List<Music>getALLMusics();

    @Update
    void updateMusic(Music m);

    @Delete
    void deleteMusic(Music m);
}
