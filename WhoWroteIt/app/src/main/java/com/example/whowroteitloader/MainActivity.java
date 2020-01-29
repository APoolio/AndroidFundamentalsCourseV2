package com.example.whowroteitloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>
{
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private TextView mEPUB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
        mEPUB = findViewById(R.id.epubText);

        //Reconnects the loader to the activity
        if(getSupportLoaderManager().getLoader(0)!=null) //If it exists
            getSupportLoaderManager().initLoader(0,null,this); //Initialize it

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
            //new FetchBook(mTitleText, mAuthorText).execute(queryString); //Calling the AsyncTask and passing in the book title given from the user

            /*                         restartLoader() method takes three args
            * A loader id, which is useful if you implement more than one loader in your activity.
            * An arguments Bundle for any data that the loader needs.
            * The instance of LoaderCallbacks that you implemented in your activity. If you want the loader to deliver the results to the MainActivity, specify this as the third argument.
            */
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

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

    //Called when you instantiate your loader
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args)
    {
        String queryString = "";
        if (args != null)
            queryString = args.getString("queryString"); //Got from the loader manager bundle key value pair

        return new BookLoader(this, queryString);
    }

    //Called when the loader's task finished. Code to update your UI with results
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data)
    {
        /* Try catch statement just in case incorrect or incomplete data is returned */

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            //Initializing variables for the for loop
            int i = 0;
            String title = null;
            String authors = null;
            String epub = null;

            while(i < itemsArray.length() && (authors == null && title == null))
            {
                //Get the current book
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                JSONObject accessInfo = book.getJSONObject("accessInfo");

                // Getting the author and the title from the current book
                // if either are empty then move on
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    epub = accessInfo.getString("country");
                    Log.d("ANDREW_TEST", epub);
                } catch (Exception e){
                    e.printStackTrace();
                }

                i++;
            }

            if(title != null && authors != null)
            {
                //Have to use .get() to dereference them since they are WeakReference objects
                mTitleText.setText(title);
                mAuthorText.setText(authors);
            }
            else
            {
                mTitleText.setText(R.string.no_result);
                mAuthorText.setText("");
            }


        } catch(JSONException e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.setText(R.string.no_result);
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }

    //Cleans up resources
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader)
    {

    }
}
