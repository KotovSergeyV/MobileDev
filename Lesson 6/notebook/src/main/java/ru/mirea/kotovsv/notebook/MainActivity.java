package ru.mirea.kotovsv.notebook;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.kotovsv.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getRoot().setBackgroundColor(Color.argb(75, 50, 255, 170));

        binding.save.setOnClickListener(view ->
        {
            if (isExternalStorageWritable()) {
                writeFileToExternalStorage(binding.editTextName.getText()+";;;"+binding.editTextQuote.getText());
            }
        });
        binding.load.setOnClickListener(view ->
        {
            if (isExternalStorageReadable()) {
                String data = readFileFromExternalStorage();
                String[] dataSplit =  data.split(";;;");
                binding.editTextName.setText(dataSplit[0].replace("[", ""));
                binding.editTextQuote.setText(dataSplit[1].replace("]", ""));
            }
        });
    }

    public	String	readFileFromExternalStorage()	{
        File	path	=	Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new	File(path,	binding.path.getText().toString());
        try	{
            FileInputStream fileInputStream	=	new	FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader	=	new	InputStreamReader(fileInputStream,	StandardCharsets.UTF_8);
            List<String> lines	=	new ArrayList<String>();
            BufferedReader	reader	=	new BufferedReader(inputStreamReader);
            String	line	=	reader.readLine();
            while	(line	!=	null)	{
                lines.add(line);
                line	=	reader.readLine();
            }
            Log.w("ExternalStorage",	String.format("Read	from	file	%s	successful",	lines.toString()));
            return lines.toString();
        }	catch	(Exception	e)	{
            Log.w("ExternalStorage",	String.format("Read	from	file	%s	failed",	e.getMessage()));
            return "Error;;;Error";
        }
    }

    /* Проверяем хранилище на доступность чтения и записи*/
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /* Проверяем внешнее хранилище на доступность чтения */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public	void	writeFileToExternalStorage(String data)	{
        File	path	=	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new File(path,	binding.path.getText().toString());
        try	{
            FileOutputStream	fileOutputStream	=	new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter	output	=	new OutputStreamWriter(fileOutputStream);
            //	Запись строки в файл
            output.write(data);
            //	Закрытие потока записи
            output.close();

        }	catch	(IOException e)	{
            Log.w("ExternalStorage",	"Error	writing	"	+	file,	e);
        }
    }
}