package algonquin.cst2335.lian0122.music;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MusicViewModel extends ViewModel {

public MutableLiveData<ArrayList<Music>> musics = new MutableLiveData<>();

    public  MutableLiveData<Music> selectedMusic = new MutableLiveData<>();
}
