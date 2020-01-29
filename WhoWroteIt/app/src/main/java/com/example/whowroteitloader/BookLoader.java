package com.example.whowroteitloader;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String>
{
    private String mQueryString; //To hold the string for the Books API query

    public BookLoader(Context context, String queryString)
    {
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading()
    {
        super.onStartLoading();
        forceLoad(); //Starts the loadInBackground() method
    }

    @Nullable
    @Override
    public String loadInBackground()
    {
        return NetworkUtils.getBookInfo(mQueryString);
    }
}
