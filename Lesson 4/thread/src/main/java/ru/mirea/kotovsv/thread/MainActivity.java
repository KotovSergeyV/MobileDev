package ru.mirea.kotovsv.thread;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

import ru.mirea.kotovsv.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private int count = 0;

    @SuppressLint("DefaultLocale")      // Появилось от String.format
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Thread mainThread = Thread.currentThread();

        binding.getRoot().setBackgroundColor( Color.argb(75, 50, 255, 170));

        binding.answerField.setText(String.format("Среднее число пар в день: %d", count));

        binding.countButton.setOnClickListener(new	View.OnClickListener() {
            @Override
            public	void	onClick(View	v)	{
                new	Thread(new	Runnable()	{
                    public	void run()	{
                        int	numberThread	=	count++;
                        Log.d("ThreadProject",	String.format("Запущен	поток	№	%d" +
                                "	студентом	группы	№	%s	номер	по	 списку	№	%d	",
                                numberThread,	"БСБО-07-22",	9));

                        Log.d("ThreadProject",	"Выполнен поток №	"	+	numberThread);

                        int c= Integer.parseInt( binding.lessonCountInput.getText().toString() )
                                /
                                Integer.parseInt( binding.dayCountInput.getText().toString() );
                        Log.d("ThreadProject", String.valueOf(c));
                        String answer = String.format("Среднее число пар в день: %d", c);
                        runOnUiThread(() ->  binding.answerField.setText(answer));

                        long	endTime	=	System.currentTimeMillis()	+	20	*	1000;
                        while	(System.currentTimeMillis()	<	endTime)	{
                            synchronized	(this)	{
                                try	{
                                    wait(endTime	- System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(),	"Endtime:	"	+	endTime);
                                }	catch	(Exception	e)	{
                                    throw	new	RuntimeException(e);
                                }
                            }

                        }
                    }
                }).start();
            }
        });
//        TextView infoTextView = binding.answerField;
//        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
//// Меняем имя и выводим в текстовом поле
//        mainThread.setName("МОЙ НОМЕР ГРУППЫ: Бсбо-07-22, НОМЕР ПО СПИСКУ: 9, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Трасса 60");
//        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
//        Log.d("LogCatMsgDebug",	"Stack:	"	+	Arrays.toString(mainThread.getStackTrace()));
    }

}