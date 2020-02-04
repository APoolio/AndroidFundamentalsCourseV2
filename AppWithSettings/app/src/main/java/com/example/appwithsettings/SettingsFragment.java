package com.example.appwithsettings;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 *                                              From Lesson Notes
 * The reason why you are essentially replacing onCreateView() with onCreatePreferences() is because you will be adding this SettingsFragment to the existing
 * SettingsActivity to display preferences rather than showing a separate Fragment screen. Adding it to the existing Activity makes it easy to add or remove
 * a Fragment while the Activity is running. The preference Fragment is rooted at the PreferenceScreen using rootKey.
 */
public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        //Inflating our preference XML file and the rootKey is to identify the preference root
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
