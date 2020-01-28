package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.Random;

/* <Does not require any parameters for input, Not publishing the progress, Result will be a string> */
public class SimpleAsyncTask extends AsyncTask<Void, Integer, String>
{
    private WeakReference<TextView> mTextView;
    private WeakReference<TextView> mTextViewCurrTime;

    SimpleAsyncTask(TextView tv, TextView currTime)
    {
        mTextView = new WeakReference<>(tv);
        mTextViewCurrTime = new WeakReference<>(currTime);
        /* Weak references prevents a memory leak, which happens due to the Activity not being able to be
        *  garbage collected, by allowing the object held by the WeakReference to be garbage collected.  */
    }

    @Override
    protected String doInBackground(Void... voids)
    {

        //Generating the random number of milliseconds to sleep
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n * 200;
        publishProgress(s); //Calls onProgressUpdate()
        //Sleep for n time ^
        try{
            Thread.sleep(s);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //Returning a String result
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        mTextViewCurrTime.get().setText("Current sleep time set to: " + values[0] + " ms"); //You have to call .get() to dereference the mTextViewCurrTime since its a WeakReference
    }

    @Override
    protected void onPostExecute(String result)
    {
        mTextView.get().setText(result); //You have to call .get() to dereference the mTextView since its a WeakReference
    }
}



