package ru.mirea.kotovsv.mireaproject;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import androidx.work.Data;
import java.util.Random;

public class MyWorker extends Worker {
    static final String TAG = "MyWorker";
    public MyWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }
    @RequiresApi(api = Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: start");

        try {
            int time = new Random().nextInt(3, 8);
            Thread.sleep(time*1000);    // ИММИТАЦИЯ РАБОТЫ

            String result = "Worked " + time + " seconds";
            Log.d(TAG, "doWork: sucess, " + result);

            Data outputData = new Data.Builder()
                    .putString("RESULT", result)
                    .build();

            return Result.success(outputData);
        }
        catch (InterruptedException e) {
            Log.e(TAG, "Error", e);
            return Result.failure();
        }
    }
}