package ru.mirea.kotovsv.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        // Получение данных из MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView textForView = findViewById(R.id.textViewBookRecieve);
            TextView textForView2 = findViewById(R.id.textViewBookRecieve2);

            String book_name = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quotes_name = extras.getString(MainActivity.QUOTES_KEY);
//
            textForView.setText(String.format("Моя любимая книга:\n %s\n", book_name));
            textForView2.setText(String.format("Цитата из книга\n %s", quotes_name));
        }
//
        EditText text1 = findViewById(R.id.editTextText);
        EditText text2 = findViewById(R.id.editTextText2);
//        // Отправка введенных пользователем данных по нажатию на кнопку
         findViewById(R.id.button2).setOnClickListener(v -> {
                String userInput = "Название Вашей любимой книги:\n"
                        +text1.getText().toString() +
                        "\nЦитата:\n" + text2.getText().toString();
                Intent data = new Intent();
                data.putExtra(MainActivity.USER_MESSAGE, userInput);
                setResult(Activity.RESULT_OK, data);
                finish();
            });
    }
}