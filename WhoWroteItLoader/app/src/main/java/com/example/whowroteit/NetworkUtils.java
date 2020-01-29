package com.example.whowroteitloader;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils
{
    /* The query parameters are separated to be reused with different URIs*/

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    //Takes a string and returns a JSON from the API
    static String getBookInfo(String queryString)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try
        {
            /* Uri.buildUpon() returns a URI.Builder */
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builtURI.toString()); //Building the URL to request

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            /* Getting the response using an InputStream, BufferReader, and a StringBuilder */
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            /* Read the input line by line while there is input left to read */
            String line;
            while((line = reader.readLine()) != null)
            {
                /*                  From Lesson Notes
                  // Since it's JSON, adding a newline isn't necessary (it won't
                  // affect parsing) but it does make debugging a *lot* easier
                  // if you print out the completed buffer for debugging.
                */
                builder.append(line);
                builder.append("\n");
            }

            //Response failed
            if(builder.length() == 0)
                return null;

            bookJSONString = builder.toString();

        } catch (IOException e){
            e.printStackTrace();
        } finally{
            //Connection worked but needs to be closed
            if(urlConnection != null)
                urlConnection.disconnect();

            //Response was read properly
            if(reader != null)
            {
                try {
                     reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}
