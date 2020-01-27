package com.example.four_images;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ImageView redCircle = findViewById(R.id.circle);
        ImageView blueLine = findViewById(R.id.line);
        ImageView androidSym = findViewById(R.id.android);
        ImageView yellowSquare = findViewById(R.id.square);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
