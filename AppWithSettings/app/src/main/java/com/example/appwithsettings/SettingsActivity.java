package com.example.appwithsettings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity
{
    /* Use getFragmentManager() if the class extends Activity and the Fragment extends PreferenceFragment. */
    /* Use getSupportFragmentManager() if the class extends AppCompatActivity and the Fragment extends PreferenceFragmentCompat. */

    //Key for Shared preference file
    public static final String KEY_PREF_EXAMPLE_SWITCH = "example_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Adding the fragment to our activity so that our fragment appears as the main content of the activity
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
