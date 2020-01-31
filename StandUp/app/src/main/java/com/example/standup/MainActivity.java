package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
{

    private ToggleButton mAlarm;
    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID = 0; //ID for notification
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel"; //ID for our notification channel

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarm = findViewById(R.id.alarmToggle);

        //creating our notification channel
        createNotificationChannel();

        //Initializing our Notification Manager
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Initializing the alarm service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onCheckedChange(View view)
    {
        String toastMessage;

        //Setting a timer of 15 minutes for our notification to be sent off
        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

        //The Pending Intent to be launched when the the 15 minute timer goes off
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        //Enabling or disabling the alarm button
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        mAlarm.setChecked(alarmUp);

        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(mAlarm.isChecked())
        {
            //Notifications are turned on so deliver them
            //deliverNotification(MainActivity.this);

            //sets the alarm to go off repeatedly in approximately 15 minutes
            //Approximately due to it being okay if our notification deviates a little from 15 mins
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);

            //Set the toast message for the "on" case.
            toastMessage = "Stand Up Alarm On!";
        }
        else
        {
            //Turn off all notifications
            mNotificationManager.cancelAll();

            //Cancel the alarm
            if(alarmManager != null)
                alarmManager.cancel(notifyPendingIntent);

            //Set the toast message for the "off" case.
            toastMessage = "Stand Up Alarm Off!";
        }

        //Show a toast to say the alarm is turned on or off.
        Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT).show();
    }

    public void createNotificationChannel()
    {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Creating our notificationChannel with our desired parameters
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up Notification!", NotificationManager.IMPORTANCE_HIGH);

        //Our set parameters
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
        mNotificationManager.createNotificationChannel(notificationChannel);
    }

    //Not needed anymore. Used an Broadcast Receiver instead in place of it
    //private void deliverNotification(Context context) { //Moved code to onReceive() }

    public void nextAlarm(View view)
    {
        if(alarmManager.getNextAlarmClock() != null)
        {
            String nextAlarm = "Next alarm at: " + alarmManager.getNextAlarmClock().toString();
            Toast.makeText(MainActivity.this, nextAlarm, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MainActivity.this, "No alarm set.", Toast.LENGTH_SHORT).show();
    }
}
