package com.example.standup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver
{
    private static final int NOTIFICATION_ID = 0; //ID for notification
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel"; //ID for our notification channel
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Initializing our Notification Manager
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Pending Intent that we will use for the notification content intent
        Intent contentIntent = new Intent(context, MainActivity.class);
        //FLAG_UPDATE_CURRENT flag tells the system to use the old Intent but replace the extras
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Building our notification with our set parameters
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //Deliver our notification to the notification bar
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
