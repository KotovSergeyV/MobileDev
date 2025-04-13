package ru.mirea.kotovsv.dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;

public class MyTimeDialog extends DialogFragment {

    @Override
    public TimePickerDialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Создаем TimePickerDialog с анонимным слушателем
        return new TimePickerDialog(
                requireActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Обработка выбранного времени
                        String time = hourOfDay + ":" + minute;
                        showSnackbar("Выбрано время: " + time);
                    }
                },
                hour,
                minute,
                DateFormat.is24HourFormat(requireContext())
        );
    }

    private void showSnackbar(String message) {
        if (getActivity() != null) {
            Snackbar.make(
                    getActivity().findViewById(android.R.id.content),
                    message,
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }
}