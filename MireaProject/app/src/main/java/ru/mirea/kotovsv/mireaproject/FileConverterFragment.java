package ru.mirea.kotovsv.mireaproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.mirea.kotovsv.mireaproject.databinding.FragmentFileConverterBinding;
import ru.mirea.kotovsv.mireaproject.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileConverterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileConverterFragment extends Fragment {


    private FragmentFileConverterBinding binding;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FileConverterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FileConverterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FileConverterFragment newInstance(String param1, String param2) {
        FileConverterFragment fragment = new FileConverterFragment();
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
        binding = FragmentFileConverterBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        LinearLayout root = binding.getRoot();
        root.setBackgroundColor(Color.argb(75, 50, 255, 170));

        binding.btnToUpper.setOnClickListener(v -> convertToUpper());
        binding.btnToLower.setOnClickListener(v -> convertToLower());
        binding.btnSave.setOnClickListener(v -> saveToFile());

        return root;
    }
    private void convertToUpper() {
        String input = binding.inputText.getText().toString();
        String result = input.toUpperCase();
        binding.resultText.setText("Результат: " + result);
    }

    private void convertToLower() {
        String input = binding.inputText.getText().toString();
        String result = input.toLowerCase();
        binding.resultText.setText("Результат: " + result);
    }

    private void saveToFile() {
        String textToSave = binding.resultText.getText().toString();
        if (textToSave.isEmpty()) {
            Toast.makeText(getContext(), "Нет данных для сохранения", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File file = new File(getContext().getFilesDir(), "converted_text.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(textToSave.getBytes());
            fos.close();
            Toast.makeText(getContext(), "Файл сохранен: " + file.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Ошибка сохранения: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}