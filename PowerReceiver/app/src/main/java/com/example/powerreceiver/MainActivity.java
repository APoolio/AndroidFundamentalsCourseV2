package com.example.powerreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

/*                                          From Lesson Notes
* Important: Although intents are used both for sending broadcasts and starting activities with startActivity(Intent), these actions are completely unrelated.
* Broadcast receivers can't see or capture an Intent that's used to start an activity.
* Likewise, when you broadcast an Intent, you can't use that Intent to find or start an activity.
*/

public class MainActivity extends AppCompatActivity
{
    private CustomReceiver mReceiver = new CustomReceiver();
    private HeadPhoneReceiver mHeadPhoneReceiver = new HeadPhoneReceiver();
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*                      From Lesson Notes
            * Intent filters specify the types of intents a component can receive
            * When the system receives an Intent as a broadcast, it searches the broadcast receivers based on the action
              value specified in the IntentFilter object.
        */

        // When one of the two actions specified below happens the onReceive function picks up the intent
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(mReceiver, filter); //register the receiver with the filters given

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));

        IntentFilter headphonesFilter = new IntentFilter();
        headphonesFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        this.registerReceiver(mHeadPhoneReceiver, headphonesFilter);

    }

    @Override
    protected void onDestroy()
    {
        //Unregistering the receiver since it is dynamic and could cause memory leaks
        //Dynamic receivers must be unregistered when no longer needed
        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        this.unregisterReceiver(mHeadPhoneReceiver);
        super.onDestroy();
    }

    public void sendCustomBroadcast(View view)
    {
        /*                                      From Lesson Notes
        * Because this broadcast is meant to be used solely by your app, use LocalBroadcastManager to manage the broadcast.

        * LocalBroadcastManager is a class that allows you to register for and send broadcasts that are of interest to components within your app.
        * By keeping broadcasts local, you ensure that your app data isn't shared with other Android apps. Local broadcasts keep your information
        * more secure and maintain system efficiency.
        */

        Intent customerBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customerBroadcastIntent);
    }
}
