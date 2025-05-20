package ru.mirea.kotovsv.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.kotovsv.lesson6.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getRoot().setBackgroundColor(Color.argb(75, 50, 255, 170));

        SharedPreferences sharedPref = getSharedPreferences("mirea_settings",	Context.MODE_PRIVATE);
        SharedPreferences.Editor editor	= sharedPref.edit();

        binding.saveButton.setOnClickListener(view ->
        {
            editor.putString("GROUP", String.valueOf(binding.groupNum.getText()));
            editor.putInt("NUMBER", Integer.valueOf( String.valueOf(binding.listNum.getText() )) );
            editor.putString("FAV_MOVIE", String.valueOf(binding.movie.getText()));
            editor.apply();
        });

         binding.groupNum.setText( sharedPref.getString("GROUP", "unknown"));
         binding.listNum.setText(String.valueOf(sharedPref.getInt("NUMBER", 0)));
         binding.movie.setText( sharedPref.getString("FAV_MOVIE", "unknown"));

    }
}