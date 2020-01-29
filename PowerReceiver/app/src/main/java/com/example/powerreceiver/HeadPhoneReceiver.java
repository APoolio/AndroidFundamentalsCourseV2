package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HeadPhoneReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String intentAction = intent.getAction();

        if(intentAction != null)
        {
            String toastMessage =  "Unknown Action";

            switch (intentAction)
            {
                case Intent.ACTION_HEADSET_PLUG:
                    toastMessage = "Headphones Plugged In.";
                    break;
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
