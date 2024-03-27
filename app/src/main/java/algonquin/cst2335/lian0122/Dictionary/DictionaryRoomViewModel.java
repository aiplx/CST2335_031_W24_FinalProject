package algonquin.cst2335.lian0122.Dictionary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class DictionaryRoomViewModel extends ViewModel {
    private RequestQueue requestQueue;
    public MutableLiveData<ArrayList<DictionaryMessage>> definitions = new MutableLiveData<>();
    public LiveData<ArrayList<DictionaryMessage>> getDefinitions() {
        return definitions;
    }
}