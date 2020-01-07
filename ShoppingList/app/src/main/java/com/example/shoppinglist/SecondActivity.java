package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity
{
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();
    public static final String EXTRA_REPLY = "com.example.shoppinglist.extra.REPLY";
    private Button item;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Intent intent = getIntent();
    }


    public void returnItem(View view) {
        item = (Button)view;
        String itemName = item.getText().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_REPLY, itemName);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
