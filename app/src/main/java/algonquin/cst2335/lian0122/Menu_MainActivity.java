/**
 * Main activity class of the application.
 * Entry point for the app, handling initial navigation.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.li000543.Recipe.RecipeMain;
import algonquin.cst2335.lian0122.Dictionary.DictionaryRoom;
import algonquin.cst2335.lian0122.databinding.ActivityMainBinding;
import algonquin.cst2335.lian0122.music.MusicActivity;

/**
 * Main activity class for the application.
 * Entry point of the app, handling initial user navigation.
 */
public class Menu_MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * Initializes the activity and sets up the UI components.
     * @param savedInstanceState If the activity is being re-initialized after being shut down, this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOpenSunriseSunsetLookup.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MainActivity.this, Sun_MainActivity.class);
            startActivity(intent);
        });

        binding.dictionaryMainPage.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MainActivity.this, DictionaryRoom.class);
            startActivity(intent);
        });

        binding.recipeMainPage.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MainActivity.this, RecipeMain.class);
            startActivity(intent);
        });

        binding.musicMainPage.setOnClickListener(v -> {
            Intent intent = new Intent(Menu_MainActivity.this, MusicActivity.class);
            startActivity(intent);
        });
    }
}

