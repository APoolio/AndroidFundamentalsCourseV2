package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/* AsyncTask to fetch a book the user has specified using Google Books API
*  It is good to use an AsyncTask for this feature do to it requiring network connectivity
*  which could slow down your app */

public class FetchBook extends AsyncTask<String, Void, String>
{
    //WeakReference to prevent memory leakage by allowing garbage collection to happen on these TextViews
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    FetchBook(TextView titleText, TextView authorText)
    {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }

    @Override
    protected String doInBackground(String... strings)
    {
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
    }
}
