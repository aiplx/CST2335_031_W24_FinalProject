/**
 * Name: Jialin Wang
 * ID#: 041041336
 * Section: 031
 * Description: Final project for the course CST2335.
 * ViewModel class for managing music data and selected music item.
 */
package algonquin.cst2335.lian0122.music;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class MusicViewModel extends ViewModel {

    /** MutableLiveData for storing list of music items. */
    public MutableLiveData<ArrayList<Music>> musics = new MutableLiveData<>();

    /** MutableLiveData for storing the selected music item. */
    public MutableLiveData<Music> selectedMusic = new MutableLiveData<>();
}
