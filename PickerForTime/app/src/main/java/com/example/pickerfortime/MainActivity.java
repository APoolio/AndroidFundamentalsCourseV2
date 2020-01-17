package com.example.pickerfortime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /* Display the Time Picker dialog to the user */
    public void showTimePicker(View view)
    {
        /* Dialog Fragment is a fragment that displays a dialog window on top of its activity's window. */
        DialogFragment newFragment = new TimePickerFragment();

        /* getSupportFragmentManager() manages the fragment that you are calling */
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }



    /* User has picked a time and is being displayed to them by a Toast for confirmation */
    public void processTimePickerResult(int hour, int minute)
    {
        String sHour = Integer.toString(hour);
        String sMinute = Integer.toString(minute);
        String timeMessage = (sHour + ":" + sMinute);

        Toast.makeText(this, "Time: " + timeMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}
