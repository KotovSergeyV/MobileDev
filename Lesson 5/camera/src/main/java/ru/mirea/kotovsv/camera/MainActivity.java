package ru.mirea.kotovsv.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.kotovsv.camera.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private boolean isWork = false;
    private Uri imageUri;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ConstraintLayout root = binding.getRoot();
        root.setBackgroundColor(Color.argb(75, 50, 255, 170));

        //  Инициализация лаунчера разрешений
//        Создает лаунчер для запроса нескольких разрешений одновременно
//        Использует контракт RequestMultiplePermissions, специально созданный для запросов разрешений
//        Лямбда-функция - это колбэк, который выполняется после того, как пользователь ответит на диалог разрешений

        ActivityResultLauncher<String[]> requestPermissionLauncher;
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean allGranted = true;
                    for (Boolean granted : result.values()) {
                        allGranted = allGranted && granted;
                    }
                    isWork = allGranted;
                    Log.d("CameraDebug", "Permissions granted: " + isWork);
                }

        );
        // проверка Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11+
            if (Environment.isExternalStorageManager()) {
                requestPermissionLauncher.launch(new String[]{Manifest.permission.CAMERA});
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
                isWork = false;
            }
        }

        // Инициализация лаунчера активности камеры
//        При запуске с интентом камеры открывает системное приложение камеры

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
//                    RESULT_OK: пользователь успешно сделал фото
//                    imageUri != null: у нас есть валидный URI сохраненного изображения
                    if (result.getResultCode() == Activity.RESULT_OK && imageUri != null) {
                        binding.imageView.setImageURI(imageUri);
                    }
                }
        );


        binding.imageView.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	v)	{
                Intent	cameraIntent	=	new	Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //	проверка на наличие разрешений для камеры
                if	(isWork)	{
                    try	{
                        File	photoFile	=	createImageFile();
                        String	authorities	= "ru.mirea.kotovsv.fileprovider";
                        imageUri	=	FileProvider.getUriForFile(MainActivity.this,	authorities,	photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,	imageUri);
                        cameraActivityResultLauncher.launch(cameraIntent);
                    }	catch	(IOException	e)	{
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
}