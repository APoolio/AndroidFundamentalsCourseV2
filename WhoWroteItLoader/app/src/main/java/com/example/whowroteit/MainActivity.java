package com.example.whowroteit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.hardware.input.InputManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
    }

    public void searchBooks(View view)
    {
        //Book we will be searching from user input
        String queryString = mBookInput.getText().toString();

        /* Hides the keyboard after user has clicked the "Search Books" button */
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager != null)
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        /* Checking if the network connection is available and the state of the connection */
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connMgr != null)
            networkInfo = connMgr.getActiveNetworkInfo();

        //There is a network connection and the user has given input
        if(networkInfo != null && networkInfo.isConnected() && queryString.length() != 0)
        {
            new FetchBook(mTitleText, mAuthorText).execute(queryString); //Calling the AsyncTask and passing in the book title given from the user
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        }
        else
        {
            // User did not specify a search term
            if(queryString.length() == 0)
            {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            }
            //Network connection error
            else
            {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }
    }
}
