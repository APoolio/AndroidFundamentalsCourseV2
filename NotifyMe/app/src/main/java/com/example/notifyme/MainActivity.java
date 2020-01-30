package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    private Button button_notify;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager; //NotificationManager is sued to deliver notifications to the user
    private static final int NOTIFICATION_ID = 0; //ID of a specific notification
    private Button button_cancel;
    private Button button_update;

    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION"; //Represents the update notification action for our broadcast
    private NotificationReceiver mReceiver = new NotificationReceiver(); //Our custom broadcast Receiver


    //Subclass for a broadcast receiver that calls updateNotification() when the user clicks the notification
    public class NotificationReceiver extends BroadcastReceiver
    {
        public NotificationReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            updateNotification();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_cancel = findViewById(R.id.cancel);
        button_update = findViewById(R.id.update);

        createNotificationChannel();
        setNotificationButtonState(true, false, false);

        //Used Have to register our receiver since it is dynamic
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        //Unregistering the receiver to prevent memory leakage
        unregisterReceiver(mReceiver);
    }

    //Function that sends the notification
    public void sendNotification(View view)
    {
        /* Pending intent to update the notification via the "Update Me" button */
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT); //FLAG_ONE_SHOT for the pending intent to only be used once

        //Building the notification with our getNotificationBuilder function
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        //Adding an action but to our notification in the notification bar
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent); //Adding an action to our notification with an image

        //Delegating which buttons are able to be clicked at the current state
        setNotificationButtonState(false, true, true);

        //Deliver our notification to the notification bar
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    //Creating the actual notification displayed to the user
    private NotificationCompat.Builder getNotificationBuilder()
    {
        /*                                          From Lesson Notes
         *   By using a PendingIntent to communicate with another app, you are telling that app to execute some predefined code at some point in the future.
         *   It's like the other app can perform an action on behalf of your app.
         */

        //Pending intent that is sent when the user clicks on the notification in the notification bar
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Building the actual notification
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent) //sets the content intent
                .setAutoCancel(true) //AutoCancel closes the notification when the user taps on it
                .setPriority(NotificationCompat.PRIORITY_HIGH) //PRIORITY_HIGH drops drops down on the user's current activity as a "heads up"
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    //Describing notification channel as a whole
    public void createNotificationChannel()
    {

        //Notification Manager object
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //Creating the notification channel for our notification(s)
        //NotificationManager.IMPORTANCE_HIGH shows everywhere makes noise and peeks
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notification from Mascot");

        //Creating our channel
        mNotifyManager.createNotificationChannel(notificationChannel);
    }


    /* Updates our notification to have a picture in the notification bar */
    //Works whether our app is running or not
    public void updateNotification()
    {

        //Using bitmap to load our image
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);

        //Building our notification that we will be updating
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        //Changing our notification style to incorporate a large picture
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage).setBigContentTitle("Notification Updated!"));

        setNotificationButtonState(false, false, true);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void cancelNotification()
    {
        setNotificationButtonState(true, false, false);

        //Cancels the notification in the notification bar
        mNotifyManager.cancel(NOTIFICATION_ID);
    }

    /* Depicts which buttons are eligible to be clicked */
    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled)
    {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }
}
