package algonquin.cst2335.lian0122;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import algonquin.cst2335.lian0122.music.Music;
import algonquin.cst2335.lian0122.music.MusicDAO;
import algonquin.cst2335.lian0122.music.MusicDatabase;

@RunWith(AndroidJUnit4.class)
public class MusicDAOTest {

    private MusicDatabase musicDatabase;
    private MusicDAO musicDAO;

    @Before
    public void setUp() {
        musicDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), MusicDatabase.class).build();
        musicDAO = musicDatabase.musicDAO();
    }

    @After
    public void tearDown() {
        musicDatabase.close();
    }

    @Test
    public void testInsertMusic() {
        Music music = new Music(1, "Song Title", 180, "Album Name", "image_url", 123, "file_name");
        long id = musicDAO.insertMusic(music);
        assertNotNull(id);
        assertEquals(1, id);
    }

   

    @Test
    public void testUpdateMusic() {
        Music music = new Music(1, "Song Title", 180, "Album Name", "image_url", 123, "file_name");
        musicDAO.insertMusic(music);
        music.setDuration(200);
        musicDAO.updateMusic(music);
        List<Music> musicList = musicDAO.getALLMusics();
        assertNotNull(musicList);
        assertEquals(1, musicList.size());
        assertEquals(200, musicList.get(0).getDuration());
    }

    @Test
    public void testDeleteMusic() {
        Music music = new Music(1, "Song Title", 180, "Album Name", "image_url", 123, "file_name");
        musicDAO.insertMusic(music);
        musicDAO.deleteMusic(music);
        List<Music> musicList = musicDAO.getALLMusics();
        assertNotNull(musicList);
        assertEquals(0, musicList.size());
    }
}
