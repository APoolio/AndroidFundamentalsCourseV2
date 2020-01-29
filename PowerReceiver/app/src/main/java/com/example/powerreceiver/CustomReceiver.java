package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/* Broadcast receivers are wrapped around with Intent Objects */

public class CustomReceiver extends BroadcastReceiver
{
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    /* When a broadcast receiver intercepts a broadcast that it's registered for, the Intent is delivered to the receiver's onReceive() method. */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String intentAction = intent.getAction(); //Get the action of the intent

        if (intentAction != null)
        {
            String toastMessage = "Unknown intent action";
            switch(intentAction)
            {
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power Disconnected";
                    break;
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power Connected";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Customer Broadcast Received";
                    break;

            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
