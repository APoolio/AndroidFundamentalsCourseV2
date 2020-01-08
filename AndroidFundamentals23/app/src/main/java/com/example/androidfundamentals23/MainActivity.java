package com.example.androidfundamentals23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebsiteEditText = findViewById(R.id.website_edittext);
        mLocationEditText = findViewById(R.id.location_edittext);
        mShareEditText = findViewById(R.id.share_edittext);
    }

    public void openWebsite(View view) {
        String url = mWebsiteEditText.getText().toString();
        //Uri is for accessing a specific URI, URL, and URN addresses.
        Uri webpage = Uri.parse(url); //Making the url into a Uri object

        //ACTION_VIEW since we want to display the webpage
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage); //ACTION_VIEW, ACTION_EDIT, adn ACTION_DIAL (phone)

        //Making sure there is an app on the device that can carry out this activity
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d("ImplicitIntents", "Can't Handle this!");
        }
    }

    public void openLocation(View view) {
        String loc = mLocationEditText.getText().toString();
        Uri addressUri = Uri.parse(loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    public void shareText(View view) {
        String text = mShareEditText.getText().toString();

        //Mime type (Media type) -> type/subtype
        String mimeType = "text/plain";

        //Built in intent to allow the user to choose how they would like to share
        ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle(R.string.shareTextWithHint).setText(text).startChooser();
    }
}
