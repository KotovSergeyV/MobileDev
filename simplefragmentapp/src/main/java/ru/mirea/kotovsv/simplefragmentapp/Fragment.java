package ru.mirea.kotovsv.simplefragmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class Fragment extends AppCompatActivity {

    private FirstFragment fragment1;
    private SecondFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v,
                                           insets) -> {
            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                    systemBars.bottom);
            return insets;
        });


        fragment1 = new FirstFragment();
        fragment2 = new SecondFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
        Button btnSecondFragment = findViewById(R.id.btnSecondFragment);
        if (btnFirstFragment != null && btnSecondFragment != null) {


        btnFirstFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
                        fragment1).commit();
            }
        });
        btnSecondFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
                        fragment2).commit();
            }
        });
    }
    }
}