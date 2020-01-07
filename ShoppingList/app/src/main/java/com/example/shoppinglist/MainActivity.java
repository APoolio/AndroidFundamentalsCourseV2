package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private int requestCode = 1;
    public static final int TEXT_REQUEST = 1;
    private TextView[] emptyView = new TextView[10];
    public static Integer itemNumber = 0;

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
    }

    public void launchSecondActivity(View view){
        Log.d(LOG_TAG, "To shopping list!");

        if(itemNumber != 10)
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                emptyView[itemNumber].setText(reply);
                itemNumber++;

                if(itemNumber == 10){
                    Toast.makeText(MainActivity.this, "Limit reached", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
