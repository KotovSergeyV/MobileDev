package ru.mirea.kotovsv.accelerometer;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.kotovsv.accelerometer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private	SensorManager	sensorManager;
    private	Sensor	accelerometer;
    private	TextView	azimuthTextView;
    private	TextView	pitchTextView;
    private TextView rollTextView;

    @Override
    protected	void	onCreate(Bundle	savedInstanceState)	{
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getRoot().setBackgroundColor( Color.argb(75, 50, 255, 170));

        sensorManager	=	(SensorManager)	getSystemService(SENSOR_SERVICE);
        accelerometer	=	sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,	accelerometer,	SensorManager.SENSOR_DELAY_NORMAL);
        azimuthTextView	=	findViewById(R.id.textViewAzimuth);
        pitchTextView	=	findViewById(R.id.textViewPitch);
        rollTextView	=	findViewById(R.id.textViewRoll);
    }
    @Override
    protected	void	onPause()	{
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    protected	void	onResume()	{
        super.onResume();
        sensorManager.registerListener(this,	accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public	void	onSensorChanged(SensorEvent	event)	{
        if	(event.sensor.getType()	==	Sensor.TYPE_ACCELEROMETER)	{
            float	x	=	event.values[0];
            float	y	=	event.values[1];
            float	z	=	event.values[2];
            azimuthTextView.setText(String.format("Azimuth:	%s",	x));
            pitchTextView.setText(String.format("Pitch:	%s",	y));
            rollTextView.setText(String.format("Roll:	%s",	z));
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