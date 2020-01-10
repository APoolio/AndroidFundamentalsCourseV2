package com.example.hellocompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private TextView mHelloTextView;
    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelloTextView = findViewById(R.id.hello_textview);


        /* If savedInstanceState is not null then there is info the savedInstanceState bundle
           In this case it is restoring the text color */
        if(savedInstanceState != null) {
            mHelloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }


    /* onSaveInstanceState is called due to some action (i.e phone rotated)
       This is where we are saving the text color to be stored by storing it in the bundle outState */
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // save the current text color
        outState.putInt("color", mHelloTextView.getCurrentTextColor());
    }

    public void changeColor(View view)
    {
        Random random = new Random();
        String colorName = mColorArray[random.nextInt(20)]; //nextInt(x) generates random ints from 0 to x-1
        Log.d("colorName", colorName);
        /*
        *  getResources() gets all the resources for the app
        *  getIdentifier() looks up the color name in the color resources file
        *  getPackageName() is specifying
        *
        * public int getIdentifier (String name, String defType, String defPackage)

         colorResourceName is basically getting the color identifier (#234FD) that matches the colorName in the colors.xml file
         Each def name and value have their own integers at compile time
        */
        int colorResourceName = getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName());
        int colorRes = getResources().getColor(colorResourceName, this.getTheme()); //New way with minSdk at API 23

        /* ContextCompat helped with API differences in the app and resources
           Used for below API 23
           int colorRes = ContextCompat.getColor(this, colorResourceName); */


        mHelloTextView.setTextColor(colorRes); //Setting the actuall Hello World! color
        //Log.d(getClass().getName(), "value = " + colorRes);
    }
}
