package com.example.whowroteitloader;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s)
    {
        /* Try catch statement just in case incorrect or incomplete data is returned */

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            //Initializing variables for the for loop
            int i = 0;
            String title = null;
            String authors = null;

            while(i < itemsArray.length() && (authors == null && title == null))
            {
                //Get the current book
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Getting the author and the title from the current book
                // if either are empty then move on
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            if(title != null && authors != null)
            {
                //Have to use .get() to dereference them since they are WeakReference objects
                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);
            }
            else
            {
                mTitleText.get().setText(R.string.no_result);
                mAuthorText.get().setText("");
            }


        } catch(JSONException e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.get().setText(R.string.no_result);
            mAuthorText.get().setText("");
            e.printStackTrace();
        }
    }
}
