package ru.mirea.kotovsv.lesson5;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mirea.kotovsv.lesson5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getRoot().setBackgroundColor( Color.argb(75, 50, 255, 170));

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ListView listSensor	=	binding.listView;

        //	создаем	список	для	отображения	в	ListView	найденных	датчиков
        ArrayList<HashMap<String,	Object>> arrayList	=	new	ArrayList<>();
        for	(int	i	=	0;	i	<	sensors.size();	i++)	{
            HashMap<String,	Object>		sensorTypeList	=	new	HashMap<>();
            sensorTypeList.put("Name",	sensors.get(i).getName());
            sensorTypeList.put("Value",	sensors.get(i).getMaximumRange());
            arrayList.add(sensorTypeList);
        }
        //	создаем	адаптер	и	устанавливаем	тип	адаптера	- отображение	двух	полей
        SimpleAdapter mHistory	=
                new	SimpleAdapter(this,	arrayList,	android.R.layout.simple_list_item_2,
                        new	String[]{"Name",	"Value"},
                        new	int[]{android.R.id.text1,	android.R.id.text2});
        listSensor.setAdapter(mHistory);


        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,	accelerometer,	SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public	void	onSensorChanged(SensorEvent	event)	{
        if	(event.sensor.getType()	==	Sensor.TYPE_ACCELEROMETER)	{
            float	x	=	event.values[0];
            float	y	=	event.values[1];
            float	z	=	event.values[2];
            //	Пример:	Вывод	в	лог
            Log.d(MainActivity.class.getSimpleName(),	"Accelerometer:	x="	+	x	+	"	y="	+	y	+	"	z="	+	z);
            //	Можно,	например,	определить,	движется	ли	телефон
        }
    }
    @Override
    public	void	onAccuracyChanged(Sensor	sensor,	int	accuracy)	{
        if	(sensor.getType()	==	Sensor.TYPE_ACCELEROMETER)	{
            switch	(accuracy)	{
                case	SensorManager.SENSOR_STATUS_UNRELIABLE:
                    Log.d("Sensor",	"Акселерометр:	ненадёжные	данные");
                    break;
                case	SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    Log.d("Sensor",	"Акселерометр:	высокая	точность");
                    break;
            }
        }
    }
}