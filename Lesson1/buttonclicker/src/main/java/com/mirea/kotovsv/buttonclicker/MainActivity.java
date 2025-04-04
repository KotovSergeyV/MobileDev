package com.mirea.kotovsv.buttonclicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView tvOut;
    private CheckBox checkBoxMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvOut = (TextView) findViewById(R.id.tvOut);
        Button btnWho = (Button) findViewById(R.id.btnWhoAmI);
        Button btnNotMe = (Button) findViewById(R.id.btnItIsNotMe);
        checkBoxMe = (CheckBox) findViewById(R.id.checkBox);

        tvOut.setText("Oh hi!");

        View.OnClickListener oclBtnWhoAmI = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOut.setText("Мой номер по списку № 9");
                checkBoxMe.setChecked(true);
            }
        };
        btnWho.setOnClickListener(oclBtnWhoAmI);
    }
    public void onNotMeButtonClick(View view)
    {
        tvOut.setText("Это 'не' я сделал");
        checkBoxMe.setChecked(false);
        // выводим сообщение
        Toast.makeText(this, "Это 'не' я сделал", Toast.LENGTH_SHORT).show();

    }
}