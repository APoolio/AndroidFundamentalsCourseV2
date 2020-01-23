package com.example.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private int score1;
    private int score2;
    private TextView mScore1;
    private TextView mScore2;
    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore1 = findViewById(R.id.score_1);
        mScore2 = findViewById(R.id.score_2);

        if(savedInstanceState != null)
        {
            score1 = savedInstanceState.getInt(STATE_SCORE_1);
            score2 = savedInstanceState.getInt(STATE_SCORE_2);

            mScore1.setText(String.valueOf(score1));
            mScore2.setText(String.valueOf(score2));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        /* Saving the scores for when screen is rotated */
        outState.putInt(STATE_SCORE_1, score1);
        outState.putInt(STATE_SCORE_2, score2);
        super.onSaveInstanceState(outState);
    }

    public void decreaseScore(View view)
    {
        int viewID = view.getId(); //get ID for the team to decrement

        switch(viewID)
        {
            case R.id.decreaseTeam1:
                score1--;
                mScore1.setText(String.valueOf(score1));
                break;

            case R.id.decreaseTeam2:
                score2--;
                mScore2.setText(String.valueOf(score2));
        }
    }


    /* User has selected an item */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //Check if the correct item was clicked
        if (item.getItemId() == R.id.night_mode)
        {
            // Get the night mode state of the app.
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            //Set the theme mode for the restarted activity
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) //If night mode is set to NO
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else //If night mode is set to Yes
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return false;
    }


    /* Creating the options menu with the menu "main_menu" */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode(); //Checking if nightMode is on (1) or off (2)
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode); //If on change the menu item to "Day Mode"
        }
        else{ menu.findItem(R.id.night_mode).setTitle(R.string.night_mode); } //If on change the menu item to "Night Mode"
        return true;
    }

    public void increaseScore(View view)
    {
        int viewID = view.getId(); //get ID for the team to decrement

        switch(viewID)
        {
            case R.id.increaseTeam1:
                score1++;
                mScore1.setText(String.valueOf(score1));
                break;

            case R.id.increaseTeam2:
                score2++;
                mScore2.setText(String.valueOf(score2));
        }
    }
}
