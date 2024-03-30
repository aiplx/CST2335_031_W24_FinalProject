package algonquin.cst2335.lian0122.music;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

import algonquin.cst2335.lian0122.R;
import algonquin.cst2335.lian0122.databinding.ActivityMusicBinding;
import algonquin.cst2335.lian0122.databinding.SearchMusicBinding;

public class MusicActivity extends AppCompatActivity {

    ActivityMusicBinding binding;

    ArrayList<Music> songs = null;

    MusicViewModel musicModel;

    private RecyclerView.Adapter musicAdapter;

    MusicDAO mDAO;

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

        musicModel = new ViewModelProvider(this).get(MusicViewModel.class);
        songs = musicModel.musics.getValue();

        musicModel.selectedMusic.observe(this, (selectedMusic) -> {


                    if (selectedMusic != null) {
                        FragmentManager fMgr = getSupportFragmentManager();
                        MusicDetailsFragment newMusic = (MusicDetailsFragment) fMgr.findFragmentByTag(MusicDetailsFragment.TAG);

                        if (newMusic == null)

                            newMusic = new MusicDetailsFragment(selectedMusic);
                        FragmentTransaction transaction = fMgr.beginTransaction();
                        transaction.addToBackStack("any String here");
                        transaction.replace(R.id.searchFragmentLocation, newMusic);
                        transaction.commit();
                    }

    });

       MusicDatabase db = Room.databaseBuilder(getApplicationContext(),MusicDatabase.class,"Musicdb").build();
       mDAO = db.musicDAO();

       if (songs == null) {
           musicModel.musics.postValue(songs = new ArraryList<Music>());
           Executor thread = Executors.newSingleThreadExecutor();
           thread.execute(() ->
        {
        songs.addAll(mDAO.getAllMusics());
        runOnUiThread(()-> binding.musicRecycleView.setAdapter(musicAdapter));
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
        } catch (UnsupportedEncodingException e){
            throw  new RuntimeException(e);
    }

        Log.d("Music","Request URL: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET.url,null,
                (response) -> {
        try {
              JSONArray resultsArray = response.getJSONArray("data");//少了name
              if (resultsArray.length() == 0);{
            Toast.makeText(this,"Sorry,found nothing", Toast.LENGTH_SHORT).show();
                 } else {
                songs.clear();
               JSONObject position0 = resultsArray.getJSONObject(0);

               String trackList = position0.getString("tracklist");
               Log.d("music","second url");
               JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,trackList,null,
                       (response1) -> {
                       try {
                           Log.d("music","in response1");
                           JSONArray tracksArray = response1.getJsonArrary("data");//shao name
                           for (int i = 0; i < tracksArray.length(); i++) {
                               Log.d("music","for loop");
                               JsonObject track = JSONArray.getJsonObject(i);
                               long id = track.getString("title");//shao name
                               String songTitle = track.getString("title");
                               int duration = tracl.getInt("duration"); //shao name
                                       JSONObject album = track.getJSONObject("album");//shao name
                               String albumName = album.getString("title");
                               String imageUrl = album.getString("cover_big");
                               int albumId = album.getInt("id");
                               String fileName = albumId + ".jpg";
                              Music music = new Music(id, songTitle,duration,albumName,imageUrl,albumId,fileName);
                              songs.add(music);
                               musicAdapter.notifyDataSetChanged();
                              binding.musicTitleText.setText("add your favorite song");
                             File file = new  File(getFilesDir(),fileName);
                            Log.d("music App","File path:"+file.getAbsolutepath());
                            if(file.exists()){
                              Log.d("Music App","File path:" +file.getAbsolutePath());
                            }else{
                            Log.d("Music App","got in else" + imageUrl);
                                ImageRequest imageReq =new ImageRequest(imageUrl,bitmap -> {
                                 Log.d("Music App","got in imReq");
                                    FileOutputStream fOut = null;
                                   try {
                                  fOut = openFileOutput("albumId + ".jpg",Context.MODE.PROVATE);
                                  bitmap.compress(Bitmap.CompressFormat.JPEG,100,fOut);
                                  fOut.flush();
                                  fOut.close();
                                  }catch{(IOException e){
                                   e.printStackTrace();
                                   }
                                  },1024,1024, ImageView.ScaleType.CENTER,null, //shao
                                 (error) ->{
                                 Log.d("music",error. toSting());
                            });
                          queue.add(imgReq);
                          }
                           }
} catch (JSONException e {
    throw new RuntimeException(e);          }
}
},error -> {
               Log.d("music","Second api"+ error. toString());
});
queue.add(request1);
}catch(JSONException e){
Log.d("music","try first api");
e.printStackTrace();
}

},
(error) -> {
Log.d("music", "request error");
});
queue.add(request);
});
binding.musicRecycleView.setAdapter(musicAdapter = new RecyclerView.Adapter<MyRowHolder>(){
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchMusicBinding binding = SearchMusicBinding.inflate(getLayoutInflater(),parent,false);
        return new  MyRowHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
               Music obj = songs.get(position);
               File file = new File(getFilesDir(),obj.getImgUrl();
               Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath();
               holder.musicName.setText(obj.getSongTitle();
             //holder.musicIcon.setImageBitmap(theImage);
            }
        @Override
        public int getItemCount() {return songs.size();}
                     });
binding.musicRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    public class MyRowHolder extends RecyclerView.ViewHolder{
public TextView musicName;

Public ImageView musicIcon;

public MyRowHolder(@NonNull View itemView){
  super(itemView);
itemView.setOnClickListener(
       clk -> {
                  int position = getAbsoluteAdapterPosition();
                   Music selected = songs.get(position);
                   musicModel.selectedMusic.postValue(selected);
});
                    musicName = itemView.findViewByid(R.id.musicResult);
    musicIcon = itemView.findViewByid(R.id.Icon);

@override
   public  boolean onCreateOptionMenu(Menu menu){
            super.onCreateOptionMenu(menu);
    getMenuInflater().inflate(R.menu.music_menu,menu);
     return true;
}

             @override
             public boolean onOptionsItemSelected(@NonNull.MenuItem item){
             switch(item.getItem()){
                 case  R.id.favoriteMusic:
                     Intent nextPage = new Intent(MusicActivity.this, MusicActivity.class);
                    startActivity(nextPage);
                    break;

                 case R.id.addItem:
                    if (musicModel.selectedMusic != null) {
                     int position = songs.indexOf(musicModel.selectedMusic.getValue());
                     if (position != -1 {
                         Music toSave = songs.get(position);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MusicActivity.this);
                           Executor thread = Executors.newSingleTheadExecutor();
                            thread.execute(() ->{
                             try {
                            Log.d("Music","try insert existing record");
                             }