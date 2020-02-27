package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity
{
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "com.example.twoactivities.extra.REPLY";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activitiy);

        Log.d(LOG_TAG, "onCreate");

        //Receive the intent
        Intent intent = getIntent();

        //Retrieve the string stored in the intent with EXTRA_MESSAGE tag
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public void returnReply(View view)
    {
        Log.d(LOG_TAG, "End SecondActivity");
        mReply = findViewById(R.id.editText_second);
        String reply = mReply.getText().toString();

        //Create an intent for a reply
        Intent replyIntent = new Intent();

        //Store the response into the replyIntent
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
