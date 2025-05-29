package ru.mirea.kotovsv.employeedb;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = App.getInstance().getDatabase();
        SuperHeroDao superheroDao = db.superheroDao();

        // Create employee without setting ID
        SuperHero employee = new SuperHero();
        employee.name = "John Smith";
        employee.powerName = "Fly";

        superheroDao.insert(employee);

        List<SuperHero> employees = superheroDao.getAll();

        if (!employees.isEmpty()) {
            SuperHero firstEmployee = employees.get(0);

            firstEmployee.powerName = "Teleport";
            superheroDao.update(firstEmployee);

            Log.d("DataBase", firstEmployee.name + " " + firstEmployee.powerName);
        }
    }
}