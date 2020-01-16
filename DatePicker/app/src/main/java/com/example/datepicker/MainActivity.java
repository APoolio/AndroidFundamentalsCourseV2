package com.example.datepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/* Touched:
*   - Fragments
*   - DatePicker Fragment specifically
*   - Calendar functionality
* */

public class MainActivity extends AppCompatActivity implements DataPickerFragment.OnFragmentInteractionListener
{
    Button mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatePicker = findViewById(R.id.datePicker);
    }

    public void showDatePicker(View view)
    {
        DialogFragment newFragment = new DataPickerFragment();

        /* getSupportFragmentManager() manages the fragment that you are calling */
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
    }

    public void processDataPickerResult(int year, int month, int day)
    {
        // The month integer returned by the date picker starts counting at 0 for January,
        // so you need to add 1 to it to show months starting at 1.
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this, "Date: " + dateMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}
