/*
 * Student Name: Ping Liang
 * Lab section: 031
 * Created: Mar 25, 2024
 * Purpose: Created the view model for DictionaryRoom to hold the data when do rotation
 */
package algonquin.cst2335.lian0122.Dictionary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * ViewModel class for the DictionaryRoom application.
 * This class is responsible for managing UI-related data in a lifecycle-conscious way.
 * It allows data to survive configuration changes such as screen rotations.
 */
public class DictionaryRoomViewModel extends ViewModel {
	/**
	 * The Volley RequestQueue used for making network requests.
	 */
	private RequestQueue requestQueue;

	/**
	 * LiveData containing the list of DictionaryMessage objects.
	 * This LiveData is observed by the UI to react to data changes.
	 */
	public MutableLiveData<ArrayList<DictionaryMessage>> definitions = new MutableLiveData<>();

	/**
	 * Returns the LiveData object containing the list of definitions.
	 * This method allows the UI to observe changes to the definitions data.
	 *
	 * @return A LiveData object containing the current list of DictionaryMessage objects representing definitions.
	 */
	public LiveData<ArrayList<DictionaryMessage>> getDefinitions() {
		return definitions;
	}
}