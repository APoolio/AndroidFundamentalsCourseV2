package com.example.androidfundamentals42challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.phoneText);

        if (editText != null)
        {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
            {

                /* The send key was pressed on the phone number keyboard so dialNumber() is called */
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent)
                {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND)
                    {
                        dialNumber();
                        handled = true;
                    }
                    return handled;
                }
            });
        }
    }


    /* When dialNumber() is called it grabs the phone number entered from the EditText field
    *  The EditText is converted to a string and then encoded into a URI object using Uri.parse(String x)
    *  An intent is created and calls the ACTION_DIAL and the phone number is set as the data being acted on
    *  If a package is found to handle the action then startActivity is called in order to carry out the action */
    private void dialNumber()
    {
        // Find the editText_main view.
        EditText editText = findViewById(R.id.phoneText);
        String phoneNum = null;
        // If the editText field is not null,
        // concatenate "tel: " with the phone number string.
        if (editText != null)
            phoneNum = "tel:" + editText.getText().toString();
        // Optional: Log the concatenated phone number for dialing.
        Log.d(TAG, "dialNumber: " + phoneNum);
        // Specify the intent.
        // ACTION_DIAL is a specific intent to call using dialer
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        // Uri.parse(String x) creates a URI object by parsing the given string and encoded into URI
        intent.setData(Uri.parse(phoneNum));
        // If the intent resolves to a package (app),
        // start the activity with the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }
}
