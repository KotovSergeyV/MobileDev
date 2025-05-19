package ru.mirea.kotovsv.mireaproject;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Locale;

import ru.mirea.kotovsv.mireaproject.databinding.FragmentCameraBinding;
import ru.mirea.kotovsv.mireaproject.databinding.FragmentSensorBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorFragment extends Fragment implements SensorEventListener {

    FragmentSensorBinding binding;
    SensorManager sensorManager;
    Sensor pressureSensor;
    boolean isSensorRegistered = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SensorFragment newInstance(String param1, String param2) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(CameraFragment);

        binding = FragmentSensorBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        FrameLayout root = binding.getRoot();
        root.setBackgroundColor(Color.argb(75, 50, 255, 170));
        binding.temperature.setText("Ожидание...");

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);


        if (pressureSensor == null) {
            Toast.makeText(getContext(), "Нет сенсора для измерения давления", Toast.LENGTH_LONG).show();
            binding.temperature.setText("Нет сенсора для измерения давления");
        }
        else {
            Toast.makeText(getContext(), "Сенсор найден", Toast.LENGTH_LONG).show();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerSensorListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterSensorListener();
    }

    private void registerSensorListener() {
        if (pressureSensor != null && !isSensorRegistered) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorRegistered = true;
            Log.d("SensorDebug", "Слушатель зарегистрирован");
        }
    }

    private void unregisterSensorListener() {
        if (isSensorRegistered) {
            sensorManager.unregisterListener(this);
            isSensorRegistered = false;
            Log.d("SensorDebug", "Слушатель отменен");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.d("SensorDebug", "Sensor upd");
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
//            Log.d("SensorDebug", "Pressure Sensor upd");
            float pressure = event.values[0];
            double boilingTemp = 100.0 + (pressure - 1013.25) * 0.037;  // Формула давления
            binding.temperature.setText(String.format(Locale.getDefault(), "%.1f", boilingTemp));
//            Toast.makeText(getContext(), String.valueOf(boilingTemp), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}