package ru.mirea.kotovsv.serviceapp;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.kotovsv.serviceapp.databinding.ActivityMainBinding;

public	class	MainActivity	extends	AppCompatActivity	{
    ActivityMainBinding binding;
    private	int	PermissionCode	=	200;
    @Override
    protected	void	onCreate(Bundle	savedInstanceState)	{
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(MainActivity.class.getSimpleName().toString(), "Разрешения получены");
        } else {
            Log.d(MainActivity.class.getSimpleName().toString(), "Нет разрешений!");
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS, FOREGROUND_SERVICE}, PermissionCode);
        }

        binding.button.setOnClickListener(view -> {
            stopService(new Intent(MainActivity.this, PlayerService.class));
            });

        binding.button2.setOnClickListener(view -> {
                Intent serviceIntent = new Intent(MainActivity.this, PlayerService.class);
                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
            });
    }
}