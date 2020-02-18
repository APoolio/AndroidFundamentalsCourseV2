package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    //Class name as LOG tag
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //Intent extra unique tag for reply intent
    public static final String EXTRA_MESSAGE = "com.example.twoactivities.extra.MESSAGE";
    private EditText mMessageEditText;
    //Unique tag for intent reply
    public static final int TEXT_REQUEST = 1;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencing views
        mMessageEditText = findViewById(R.id.editText_main);
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);

        // Checking if there is data saved in the InstanceState
        if (savedInstanceState != null)
        {
            //Reading boolean stored in InstanceState with tag "reply_visible"
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");

            if (isVisible)
            {
                //Set text stored in instanceState with tag reply_text
                mReplyTextView.setText(savedInstanceState.getString("reply_text"));
                mReplyTextView.setVisibility(View.VISIBLE);
                mReplyHeadTextView.setVisibility(View.VISIBLE);
            }
        }
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

    //When device is rotated for example
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE)
        {
            //put boolean and string into outState which can be read in the instanceState
            outState.putBoolean("reply_visible", true);
            outState.putString("reply_text", mReplyTextView.getText().toString());
        }
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

    public void launchSecondActivity(View view)
    {
        Log.d(LOG_TAG, "Button clicked!");

        //Create intent which will launch SecondActivity
        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessageEditText.getText().toString();

        //Put message into intent extra
        intent.putExtra(EXTRA_MESSAGE, message);

        //Launch the Activity
        startActivityForResult(intent, TEXT_REQUEST);
    }

    //Received a reply intent from another activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //If reply intent has TEXT_REQUEST as requestCode
        if (requestCode == TEXT_REQUEST)
        {
            //If the intent was successfully carried out
            if (resultCode == RESULT_OK)
            {
                //Get the string extra and set it
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
