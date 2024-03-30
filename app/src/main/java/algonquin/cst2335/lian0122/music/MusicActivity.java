package algonquin.cst2335.lian0122.music;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.lian0122.R;
import algonquin.cst2335.lian0122.databinding.ActivityMusicBinding;

public class MusicActivity extends AppCompatActivity {

    ActivityMusicBinding binding;

    ArrayList<Music> songs = null;

    MusicViewModel musicModel;

    private RecyclerView.Adapter musicAdapter;

    MusicDao mDAO;

    protected RequestQueue queue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        binding =ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences prefs =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.musicTextInput.setText(prefs.getString("musicName",""));
        setSupportActionBar(binding.musicToolbar);

        musicModel.selectMusic.onserve(this,(selectMusic) -> {

            if (selectMusic != null) {
                FragmentManager fMgr = getSupportFragmentManager();
                MusicDetailsFragment newMusic = (MusicDetailsFragment) fMgr.findFragmentBytag(MusicDetailsFragment.TAG);

                if (newMusic == null)

                    newMusic = new MusicDetailsFragment(selectMusic);
                FragmentTransaction transaction = fMgr.beginTransaction();
                transaction.addToBackStack("any String here");
                transaction.replace(R.id.searchFragmentLocation, newMusic);
                transaction.commit();
            }
        }
        });

       MusicDatabase db = Room.databaseBuilder(getApplicationContext(),MusicDatabase.class,"Musicdb").build();
       mDao = db.musicDao();

       if (song == null) {
           musicModel.musics.postValue(songs = new ArraryList<Music>());
           Executor thread = Executors.newSingleThreadExecutor();
           thread.execute(() ->
        {
        songs.addAll(mDAO.getAllMusics());
        runOnUiThread(()->binding.musicRecycleView.setAdapter(musicAdapter));
        });
        }
       binding.musicsearchButton.setOnclickListener(clk ->

    {
        String musicTextInput = binding.musicTextInput.getText().toString();

        sharedPreferences.Editor editor = prefs.edit();
        editor.putString("musicName", musicTextInput);
        editor.apply();

        String url = "";
        try {
            url = "https://api.deezer.com/search/artist/?q="
                    + URLEncoder.encode(musicTextInput, "UTF-8");
        } catch (UnsupportedEncodingException e){
            throw  new RuntimeException(e);
    }

        Log.d("Music","Request URL: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET.url,null,
                (response) -> {
        try {
              JSONArray resultsArray = response.getJSONArray("data");//少了name
              if (resultsArray.length() == 0);
            Toast.makeText(this,"Sorry,found nothing", Toast.LENGTH_SHORT).show();
                 } else {
songs.clear();
               JSONObject position0 = resultsArray.getJSONObject(0);


