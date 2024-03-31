/**
 * Name: Jialin Wang
 * ID#: 041041336
 * Section: 031
 * Description: Final project for the course CST2335.
 * Fragment class for displaying details of a music item.
 */
package algonquin.cst2335.lian0122.music;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.File;

import algonquin.cst2335.lian0122.databinding.MusicDetailsLayoutBinding;
import algonquin.cst2335.lian0122.music.Music;


public class MusicDetailsFragment extends Fragment {

    private Music selected;
    public static final String TAG = "MusicDetailsFragment";

    public MusicDetailsFragment() {}

    /**
     * Constructs a MusicDetailsFragment with the given Music object.
     *
     * @param m The Music object to display details for.
     */
    public MusicDetailsFragment(Music m) {
        selected = m;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        super.onCreateView(inflater, container, saveInstanceState);
        setRetainInstance(true);
        MusicDetailsLayoutBinding binding = MusicDetailsLayoutBinding.inflate(inflater, container, false);
        File file = new File(requireContext().getFilesDir(), selected.getFileName());
        if (file.exists()) {
            Log.d("Image Log", "Got large image");
            Bitmap img = BitmapFactory.decodeFile(file.getAbsolutePath());
            binding.musicImageView.setImageBitmap(img);
        }
        binding.songTitle.setText(selected.songTitle);
        binding.duration.setText("Duration" + ": " + selected.duration + "s");
        binding.albumName.setText(selected.albumName);
        return binding.getRoot();
    }
}
