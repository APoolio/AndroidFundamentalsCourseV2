package com.example.twoactivitieschallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    public static final String EXTRA_MESSAGE = "com.example.twoactivitieschallenge.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void launchTextOne(View view) {
        Intent textOne = new Intent(this, TextOne.class );
        String displayText = "hello numba one";
        textOne.putExtra(EXTRA_MESSAGE, displayText);
        startActivity(textOne);

    }

    public void launchTextTwo(View view) {
        Intent textTwo = new Intent(this, TextThree.class );
        String displayText = "hello numba two";
        textTwo.putExtra(EXTRA_MESSAGE, displayText);
        startActivity(textTwo);
    }
}
