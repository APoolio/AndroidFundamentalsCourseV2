package com.example.android.hellosharedprefs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.TextView;

public class SaveActivity extends AppCompatActivity
{

    private static final String LOG_TAG = SaveActivity.class.getSimpleName();

    //The main text view with color and count
    private TextView mMainTextView;

    //variables for color and count from the intent
    private int mCount = 0;
    private int mColor = 0;

    //Shared reference object
    private SharedPreferences mPreferences;
    //shared preference file
    private String sharedPrefFile = "com.example.android.hellosharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        mMainTextView = findViewById(R.id.count_textview);

        Intent intent = getIntent();

        mCount = intent.getIntExtra("count", 0);
        mMainTextView.setText(Integer.toString(mCount));

        mColor = intent.getIntExtra("color", 0);
        mMainTextView.setBackgroundColor(mColor);

        //Opens file at sharedPrefFile with MODE_PRIVATE
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mMainTextView.setBackgroundColor(color);
        mColor = color;
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        mMainTextView.setText(String.format("%s", mCount));
    }

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view)
    {
        // Reset count
        mCount = 0;
        mMainTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mMainTextView.setBackgroundColor(mColor);

        //Editor to clear saved data
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();

        preferenceEditor.clear();

        preferenceEditor.apply();

    }

    public void saveState(View view)
    {
        //Needed to write to a shared preference object
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //Used to store the current color and current count
        preferencesEditor.putInt("count", mCount);
        preferencesEditor.putInt("color", mColor);

        //Saving the preferences asynchronously off of the UI thread
        preferencesEditor.apply();
    }
}
