package ru.mirea.kotovsv.musicplayer;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.kotovsv.musicplayer.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean plaing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.getRoot().setBackgroundColor( Color.argb(75, 50, 255, 170));
        binding.titleText.setText("Lagtrain (ru cover)");
        binding.artistText.setText("by Sati Akura");

        binding.playBtn.setOnClickListener(click -> {
            if (plaing)
            {
                plaing = false;
                binding.playBtn.setImageResource(android.R.drawable.ic_media_play);
            }
            else
            {
                plaing = true;
                binding.playBtn.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }
}