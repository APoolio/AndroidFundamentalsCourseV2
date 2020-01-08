package com.example.androidfundamentals23task5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We are getting the incoming intent and any data/extras that are coming with it
        //URI is stored in the intent data here
        Intent intent = getIntent();
        Uri uri = intent.getData();

        //Uri is not null
        if(uri != null){
            String uri_string = R.string.uri_label + uri.toString();

            TextView textView = findViewById(R.id.text_uri_message);
            textView.setText(uri_string);
        }
    }
}
