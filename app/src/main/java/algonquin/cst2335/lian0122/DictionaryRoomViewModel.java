package algonquin.cst2335.lian0122;

import android.util.Log;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DictionaryRoomViewModel extends ViewModel {
    private RequestQueue requestQueue;
    public MutableLiveData<ArrayList<DictionaryMessage>> definitions = new MutableLiveData<>();
    public LiveData<ArrayList<DictionaryMessage>> getDefinitions() {
        return definitions;
    }
}