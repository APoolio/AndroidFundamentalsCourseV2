package com.example.androidcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Button mNotificationButton;

    //Used to deliver notifications to the user
    private NotificationManager mNotifyManager;

    //Notification ID to help us edit and update a notification
    private static final int NOTIFICATION_ID = 0;

    //Notification Channel ID
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference Button
        mNotificationButton = findViewById(R.id.createNotification);

        //Need to call so we can build the NotificationChannel
        createNotificationChannel();
    }

    //Channel is called onCreate()
    public void createNotificationChannel()
    {
        //Instantiate the NotificationManager
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Notification Channel constructed
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Simple Notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Simple Notification");

        //Create the channel with the NotificationManager
        mNotifyManager.createNotificationChannel(notificationChannel);
    }

    //Method to return a notify builder for later use when a notification needs to be created
    //Actual notification information
    private NotificationCompat.Builder getNotificationBuilder()
    {
        //Launches the application once the user clicks the notification
        //Pending intent that is sent when the user clicks on the notification in the notification bar
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setContentIntent(notificationPendingIntent)
                .setSmallIcon(R.drawable.ic_stat_name);
        return notifyBuilder;
    }

    //Called when the user clicks the "Create Notification Button"
    public void buildNotification(View view)
    {
        Toast.makeText(this, "Building the Notification", Toast.LENGTH_SHORT).show();

        //Build the notification
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        //Push the notification with a unique notification ID
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    /*public class NotificationReceiver extends BroadcastReceiver
    {
        public NotificationReceiver(){
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {

        }
    }*/
}
