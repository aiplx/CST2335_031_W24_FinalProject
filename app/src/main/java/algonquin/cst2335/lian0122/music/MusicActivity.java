/**
 * Name: Jialin Wang
 * ID#: 041041336
 * Section: 031
 * Description: Final project for the course CST2335.
 * This class represents a Music object.
 */
package algonquin.cst2335.lian0122.music;

import static android.app.ProgressDialog.show;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.li000543.Recipe.RecipeMain;
import algonquin.cst2335.lian0122.Dictionary.DictionaryRoom;
import algonquin.cst2335.lian0122.R;
import algonquin.cst2335.lian0122.Sun_MainActivity;
import algonquin.cst2335.lian0122.databinding.ActivityMusicBinding;
import algonquin.cst2335.lian0122.databinding.SearchMusicBinding;

/**
 * This class represents the main activity for the Music application.
 * It allows users to search for music, view details, add to favorites, and delete music items.
 */
public class MusicActivity extends AppCompatActivity {

    ActivityMusicBinding binding;

    ArrayList<Music> songs = null;

    MusicViewModel musicModel;

    private RecyclerView.Adapter musicAdapter;

    MusicDAO mDAO;

    protected RequestQueue queue = null;

    /**
     * Initializes the activity when created.
     * It sets up the UI, initializes ViewModel, sets up RecyclerView, and handles search button clicks.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        binding = ActivityMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.musicTextInput.setText(prefs.getString("musicName", ""));

        setSupportActionBar(binding.musicToolbar);

        musicModel = new ViewModelProvider(this).get(MusicViewModel.class);
        songs = musicModel.musics.getValue();

        musicModel.selectedMusic.observe(this, (selectedMusic) -> {


            if (selectedMusic != null) {
                FragmentManager fMgr = getSupportFragmentManager();
                MusicDetailsFragment newMusic = (MusicDetailsFragment) fMgr.findFragmentByTag(MusicDetailsFragment.TAG);

                if (newMusic == null) {

                    newMusic = new MusicDetailsFragment(selectedMusic);
                    FragmentTransaction transaction = fMgr.beginTransaction();
                    transaction.addToBackStack("any String here");
                    transaction.replace(R.id.searchFragmentLocation, newMusic);
                    transaction.commit();
                }
            }

        });

        MusicDatabase db = Room.databaseBuilder(getApplicationContext(), MusicDatabase.class, "musicdb").build();
        mDAO = db.musicDAO();

        if (songs == null) {

            musicModel.musics.postValue(songs = new ArrayList<Music>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                songs.addAll(mDAO.getALLMusics());
                runOnUiThread(() -> binding.musicRecycleView.setAdapter(musicAdapter));
            });
        }
        binding.musicSearchButton.setOnClickListener(clk -> {


            String musicTextInput = binding.musicTextInput.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("musicName", musicTextInput);
            editor.apply();

            String url = "";
            try {
                url = "https://api.deezer.com/search/artist/?q="
                        + URLEncoder.encode(musicTextInput, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            Log.d("Music", "Request URL: " + url);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response) -> {
                        try {
                            JSONArray resultsArray = response.getJSONArray("data");
                            if (resultsArray.length() == 0) {
                                Toast.makeText(this, R.string.music_notFoundToast, Toast.LENGTH_SHORT).show();
                            } else {
                                songs.clear();

                                JSONObject position0 = resultsArray.getJSONObject(0);

                                String trackList = position0.getString("tracklist");
                                Log.d("music", "second url");
                                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, trackList, null,
                                        (response1) -> {
                                            try {
                                                Log.d("music", "in response1");
                                                JSONArray tracksArray = response1.getJSONArray("data");

                                                for (int i = 0; i < tracksArray.length(); i++) {
                                                    Log.d("music", "for loop");
                                                    JSONObject track = tracksArray.getJSONObject(i);
                                                    long id = track.getLong("id");
                                                    String songTitle = track.getString("title");
                                                    int duration = track.getInt("duration");

                                                    JSONObject album = track.getJSONObject("album");
                                                    String albumName = album.getString("title");
                                                    String imageUrl = album.getString("cover_big");
                                                    int albumId = album.getInt("id");
                                                    String fileName = albumId + ".jpg";
                                                    Music music = new Music(id, songTitle, duration, albumName, imageUrl, albumId, fileName);
                                                    songs.add(music);
                                                    musicAdapter.notifyDataSetChanged();

                                                    binding.musicTitleText.setText(R.string.music_addTitle);
                                                    File file = new File(getFilesDir(), fileName);
                                                    Log.d("Music App", "File path: " + file.getAbsolutePath());
                                                    if (file.exists()) {
                                                        Log.d("Music App", "File path: " + file.getAbsolutePath());
                                                        //      musicAdapter.notifyDataSetChanged();
                                                    } else {
                                                        Log.d("Music App", "got in else " + imageUrl);
                                                        ImageRequest imgReq = new ImageRequest(imageUrl, bitmap -> {
                                                            Log.d("Music App", "got in imgReq ");
                                                            // Do something with loaded bitmap...
                                                            FileOutputStream fOut = null;
                                                            try {
                                                                fOut = openFileOutput(albumId + ".jpg", Context.MODE_PRIVATE);
                                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                                                                fOut.flush();
                                                                fOut.close();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                            //        musicAdapter.notifyDataSetChanged();
                                                        }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                                                (error) -> {
                                                                    Log.d("music", error.toString());
                                                                });
                                                        queue.add(imgReq);
                                                    }

                                                }
                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }, error -> {
                                    Log.d("music", "second api" + error.toString());
                                });
                                queue.add(request1);
                            }

                        } catch (JSONException e) {
                            Log.d("music", "try first api");
                            e.printStackTrace();
                        }
                    },
                    (error) -> {
                        Log.d("music", "request error");
                    });
            queue.add(request);


        });

        binding.musicRecycleView.setAdapter(musicAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SearchMusicBinding binding = SearchMusicBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                Music obj = songs.get(position);
                File file = new File(getFilesDir(), obj.getImgUrl());
                Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.musicName.setText(obj.getSongTitle());
                //holder.musicIcon.setImageBitmap(theImage);
            }

            @Override
            public int getItemCount() {
                return songs.size();
            }
        });
        binding.musicRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Represents a ViewHolder for the RecyclerView.
     */
    public class MyRowHolder extends RecyclerView.ViewHolder {
        public TextView musicName;

        public ImageView musicIcon;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(
                    clk -> {
                        int position = getAbsoluteAdapterPosition();
                        Music selected = songs.get(position);
                        musicModel.selectedMusic.postValue(selected);
                    });
            musicName = itemView.findViewById(R.id.musicResult);
            musicIcon = itemView.findViewById(R.id.musicIcon);
        }
    }

    /**
     * Initializes the options menu for the activity.
     * @param menu The menu to be inflated
     * @return Returns true if the menu is displayed, false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.music_menu, menu);
        return true;
    }

    /**
     * Handles options menu item clicks.
     * @param item The selected menu item
     * @return Returns true if the item is successfully handled, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favoriteMusic) {
            Intent nextPage = new Intent(MusicActivity.this, MusicActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.addItem) {
            if (musicModel.selectedMusic != null) {
                int position = songs.indexOf(musicModel.selectedMusic.getValue());
                if (position != -1) {
                    Music toSave = songs.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MusicActivity.this);
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        try {
                            Log.d("Music", "try insert existing record");
                            mDAO.insertMusic(toSave);
                            runOnUiThread(() -> {
                                Log.d("Music", "Music saved successfully");
                                Toast.makeText(MusicActivity.this, R.string.music_insertSucceedToast, Toast.LENGTH_SHORT).show();
                            });
                        } catch (Exception e) {
                            Log.d("Music", "catch exception");
                            runOnUiThread(() -> Toast.makeText(MusicActivity.this, R.string.music_alreadyInToast, Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    Toast.makeText(this, R.string.music_noSelectedToast, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (item.getItemId() == R.id.deleteItem) {
            if (musicModel.selectedMusic != null) {
                int position = songs.indexOf(musicModel.selectedMusic.getValue());
                if (position != -1) {
                    Music toDelete = songs.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MusicActivity.this);
                    builder.setMessage(getString(R.string.music_deleteAlert) + " " + toDelete.getSongTitle())
                            .setTitle("Question: ")
                            .setPositiveButton(R.string.music_yes, (dialog, cl) -> {
                                Executor thread = Executors.newSingleThreadExecutor();
                                thread.execute(() -> {
                                    mDAO.deleteMusic(toDelete);
                                });
                                songs.remove(position);
                                musicAdapter.notifyDataSetChanged();
                                getSupportFragmentManager().popBackStack(); // go back to message list
                                Snackbar.make(binding.musicRecycleView, getString(R.string.music_deleteSnackBar) + (position + 1), Snackbar.LENGTH_LONG)
                                        .setAction(R.string.music_undo, click -> {
                                            Executor thread1 = Executors.newSingleThreadExecutor();
                                            thread1.execute(() -> {
                                                mDAO.insertMusic(toDelete);
                                            });
                                            songs.add(position, toDelete);
                                            musicAdapter.notifyDataSetChanged();
                                        })
                                        .show();
                            })
                            .setNegativeButton(R.string.music_no, (dialog, cl) -> {
                            })
                            .create().show();
                } else {
                    Toast.makeText(this, R.string.music_noSelectedToast, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (item.getItemId() == R.id.helpItem) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MusicActivity.this);
            builder.setMessage(R.string.music_helpDetail)
                    .setTitle(R.string.music_helpTitle)
                    .setPositiveButton("OK", (dialog, cl) -> {
                    }).create().show();
        } else if (item.getItemId() == R.id.aboutMusic) {
            Toast.makeText(this, R.string.music_aboutToast, Toast.LENGTH_LONG).show();

        }else if (item.getItemId() == R.id.item_sun) {
            Intent recipePage = new Intent(MusicActivity.this, Sun_MainActivity.class);
            startActivity(recipePage);
            return true;
        }else if (item.getItemId() == R.id.item_recipe) {
            Intent recipePage = new Intent(MusicActivity.this, RecipeMain.class);
            startActivity(recipePage);
            return true;
        }else if (item.getItemId() == R.id.item_dictionary) {
            Intent musicPage = new Intent(MusicActivity.this, DictionaryRoom.class);
            startActivity(musicPage);
            return true;}
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }


    /**
     * Handles the configuration changes, such as rotation or language change.
     * It resolves the fragment problem when configuration changes.
     * @param newConfig The new configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (!isChangingConfigurations()) {
            // Check the current state of the back stack and pop if needed
            getSupportFragmentManager().popBackStack(MusicDetailsFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
