package com.example.androidfundamentals41;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;


/* An adapter connects your data—in this case, the array of spinner items—to the Spinner */
public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    TextView mDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mDisplayText = findViewById(R.id.displayPurchase);
        mDisplayText.setText(message);

        //Code to create the spinner and adds a listener on the spinner
        Spinner spinner = findViewById(R.id.label_spinner);
        if (spinner != null) { spinner.setOnItemSelectedListener(this); }

        //Code to fill the spinner and to change the view of the spinner if the R.layout class
        //An adapter just add your data (labels_array) to a view (spinner)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Code to specify the layout that is being used for the spinner
        if (spinner != null) { spinner.setAdapter(adapter);} //Apply it to the actual spinner
    }

    public void displayToast(String message) { Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show(); }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.sameday:
                if (checked)
                    // Same day service
                    displayToast(getString(R.string.same_day_messenger_service));
                break;
            case R.id.nextday:
                if (checked)
                    // Next day delivery
                    displayToast(getString(R.string.next_day_ground_delivery));
                break;
            case R.id.pickup:
                if (checked)
                    // Pick up
                    displayToast(getString(R.string.pick_up));
                break;
            default:
                // Do nothing.
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        Log.d("Spinner", "value = " + i);
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        //Nothing to do here
        Log.d("Spinner", "No option selected");
    }


    /* Showing how to build an alert dialog using the AlertDialog Builder */
    public void onClickShowAlert(View view)
    {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(OrderActivity.this);
        myAlertBuilder.setTitle(getString(R.string.alert2));
        myAlertBuilder.setMessage(R.string.okOrCancel);

        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                // User clicked OK button.
                Toast.makeText(getApplicationContext(), "Pressed OK", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                // User cancelled the dialog.
                Toast.makeText(getApplicationContext(), "Pressed Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.show();
    }
}
