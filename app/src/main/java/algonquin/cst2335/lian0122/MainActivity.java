package algonquin.cst2335.lian0122;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.lian0122.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOpenSunriseSunsetLookup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Sun_MainActivity.class);
            startActivity(intent);
        });
    }
}

