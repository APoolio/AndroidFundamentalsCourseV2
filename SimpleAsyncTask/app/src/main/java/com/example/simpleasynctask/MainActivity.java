package com.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView mTextView;
    private  TextView mCurrTimeView;
    private static final String TEXT_STATE = "currentText";
    private static final String TEXT_STATE_2 = "currentText1";

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
        outState.putString(TEXT_STATE_2, mCurrTimeView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        mCurrTimeView = findViewById(R.id.textView2);

        if(savedInstanceState != null)
        {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
            mCurrTimeView.setText(savedInstanceState.getString(TEXT_STATE_2));
        }
    }

    /* Method that calls the AsyncTasks */
    public void startTask(View view)
    {
        mTextView.setText(R.string.napping);

        //Execute would be where you pass the variables for the doInBackground();
        new SimpleAsyncTask(mTextView, mCurrTimeView).execute();
    }
}
