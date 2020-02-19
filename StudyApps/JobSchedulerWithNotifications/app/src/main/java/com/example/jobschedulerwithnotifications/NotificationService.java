package com.example.jobschedulerwithnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationService extends JobService
{
    //Manage Notifications sent
    NotificationManager mNotificationManager;

    //Notification Channel ID
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 1;

    /**
     * Called by the system once it determines it is time to run the job.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether or not the job was offloaded to a
     * separate thread.
     * In this case, it is false since the notification can be posted on
     * the main thread.
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        createNotificationChannel();

        NotificationCompat.Builder builder = getNotificationBuilder();
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

        return false;
    }


    /**
     * Called by the system when the job is running but the conditions are no
     * longer met.
     * In this example it is never called since the job is not offloaded to a
     * different thread.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether the job needs rescheduling.
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {
        return false;
    }


    public void createNotificationChannel()
    {
        //Instantiate as a NOTIFICATION_SERVICE
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Notification Channel constructed
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Simple Notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Simple Notification");
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
                .setSmallIcon(R.drawable.ic_beach);

        return notifyBuilder;
    }
}
