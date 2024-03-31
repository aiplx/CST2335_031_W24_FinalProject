package algonquin.cst2335.lian0122;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import algonquin.cst2335.lian0122.music.Music;

public class MusicTest {

    private Music music;

    @Before
    public void setUp() {
        music = new Music(1, "Song Title", 180, "Album Name", "image_url", 123, "file_name");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1, music.getId());
        assertEquals("Song Title", music.getSongTitle());
        assertEquals(180, music.getDuration());
        assertEquals("Album Name", music.getAlbumName());
        assertEquals("image_url", music.getImgUrl());
        assertEquals(123, music.getAlbumId());
        assertEquals("file_name", music.getFileName());

        music.setId(2);
        music.setSongTitle("New Song Title");
        music.setDuration(200);
        music.setAlbumName("New Album Name");
        music.setImgUrl("new_image_url");
        music.setAlbumId(456);
        music.setFileName("new_file_name");

        assertEquals(2, music.getId());
        assertEquals("New Song Title", music.getSongTitle());
        assertEquals(200, music.getDuration());
        assertEquals("New Album Name", music.getAlbumName());
        assertEquals("new_image_url", music.getImgUrl());
        assertEquals(456, music.getAlbumId());
        assertEquals("new_file_name", music.getFileName());
    }


    @Test
    public void testNullParametersInConstructor() {
        Music nullMusic = new Music(0, null, 0, null, null, 0, null);
        assertNotNull(nullMusic);
        assertEquals(0, nullMusic.getId());
        assertEquals(null, nullMusic.getSongTitle());
        assertEquals(0, nullMusic.getDuration());
        assertEquals(null, nullMusic.getAlbumName());
        assertEquals(null, nullMusic.getImgUrl());
        assertEquals(0, nullMusic.getAlbumId());
        assertEquals(null, nullMusic.getFileName());
    }

}