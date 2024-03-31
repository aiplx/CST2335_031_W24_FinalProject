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

import algonquin.cst2335.lian0122.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOpenSunriseSunsetLookup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Sun_MainActivity.class);
            startActivity(intent);
        });
    }
}

