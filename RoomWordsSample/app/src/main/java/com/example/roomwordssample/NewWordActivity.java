package com.example.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity
{
    private Button mSaveButton;
    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";
    private EditText mEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mSaveButton = findViewById(R.id.button_save);
        mEditTextView = findViewById(R.id.edit_word);
    }

    public void saveButton(View view)
    {
        Intent replyIntent = new Intent();

        if(TextUtils.isEmpty(mEditTextView.getText()))
        {
            setResult(RESULT_CANCELED, replyIntent);
        }
        else
        {
            String word = mEditTextView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
        }

        finish();
    }
}
