package ru.mirea.kotovsv.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnTime = findViewById(R.id.btnTime);
        Button btnDate = findViewById(R.id.btnDate);
        Button btnProgress = findViewById(R.id.btnProgress);

        btnTime.setOnClickListener(v -> {
            showDialog(new MyTimeDialog());
            showSnackbar("Time dialog shown");
        });
        btnDate.setOnClickListener(v -> {
            showDialog(new MyDateDialog());
            showSnackbar("Date dialog shown");
        });
        btnProgress.setOnClickListener(v -> {
            showDialog(new MyProgressDialog());
            showSnackbar("Progress dialog shown");
        });

        //btnDate.setOnClickListener(v -> showDialog(new MyDateDialogFragment()));
        //btnProgress.setOnClickListener(v -> showDialog(new MyProgressDialogFragment()));
    }

    private void showSnackbar(String message) {
        Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT
        ).show();
    }




    private void showDialog(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    public void onClickShowDialog(View view) {
        MyAlertDialog dialogFragment = new MyAlertDialog();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }
}