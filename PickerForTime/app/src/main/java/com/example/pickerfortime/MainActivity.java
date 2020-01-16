package com.example.pickerfortime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDatePicker(View view)
    {
        DialogFragment newFragment = new TimePickerFragment();

        /* getSupportFragmentManager() manages the fragment that you are calling */
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
    {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minuteE = c.get(Calendar.MINUTE);
    }

    public void processTimePickerResult(int hour, int minute)
    {
        String sHour = Integer.toString(hour);
        String sMinute = Integer.toString(minute);
        String timeMessage = (sHour + ":" + sMinute);

        Toast.makeText(this, "Time: " + timeMessage, Toast.LENGTH_SHORT).show();
    }
}
