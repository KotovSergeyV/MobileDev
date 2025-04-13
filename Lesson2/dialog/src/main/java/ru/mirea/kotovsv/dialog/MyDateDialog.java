package ru.mirea.kotovsv.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import java.util.Date;

public class MyDateDialog extends DialogFragment {

    @Override
    public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(
                requireActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String data = dayOfMonth + "." + month +"." + year;
                        showSnackbar("Выбрано: " + data);
                    }
                },
                year,
                month,
                dayOfMonth
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
