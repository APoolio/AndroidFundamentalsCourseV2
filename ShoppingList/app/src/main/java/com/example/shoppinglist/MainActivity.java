package com.example.shoppinglist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private int requestCode = 1;
    public static final int TEXT_REQUEST = 1;
    private TextView[] emptyView = new TextView[10];
    public static Integer itemNumber = 0;
    private TextView mStoreEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyView[0] = findViewById(R.id.textView);
        emptyView[1] = findViewById(R.id.textView2);
        emptyView[2] = findViewById(R.id.textView3);
        emptyView[3] = findViewById(R.id.textView4);
        emptyView[4] = findViewById(R.id.textView5);
        emptyView[5] = findViewById(R.id.textView6);
        emptyView[6] = findViewById(R.id.textView7);
        emptyView[7] = findViewById(R.id.textView8);
        emptyView[8] = findViewById(R.id.textView9);
        emptyView[9] = findViewById(R.id.textView10);

        mStoreEditText = findViewById(R.id.storeToFind);
    }

    public void launchSecondActivity(View view)
    {
        Log.d(LOG_TAG, "To shopping list!");

        if (itemNumber != 10)
        {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent, requestCode);
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState)
//    {
//        super.onSaveInstanceState(outState);
//        if (mReplyHeadTextView.getVisibility() == View.VISIBLE)
//        {
//            outState.putBoolean("reply_visible", true);
//            outState.putString("reply_text", mReplyTextView.getText().toString());
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                emptyView[itemNumber].setText(reply);
                itemNumber++;

                if (itemNumber == 10)
                {
                    Toast.makeText(MainActivity.this, "Limit reached", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void findStore(View view)
    {
        String storeAddress = null;
        String storeToFind = mStoreEditText.getText().toString();
        //String storeGeo = "geo:0,0?=" + storeToFind; /* Method failed */
        String googleSearch = "https://www.google.com/maps/search/?api=1&query="; //Had to use Google Maps search query to filter for the store they wanted
        try
        {
            storeAddress = googleSearch + URLEncoder.encode(storeToFind, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        //Log.d("ImplicitIntent", storeAddress);
        Uri storeUri = Uri.parse(storeAddress);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, storeUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(mapIntent);
        }
        else
        {
            Log.d("ImplicitIntent", "Can't find an application to carry out task!");
        }

    }
}
