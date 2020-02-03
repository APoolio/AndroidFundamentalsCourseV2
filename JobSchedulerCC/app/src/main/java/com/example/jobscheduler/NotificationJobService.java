package com.example.jobscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService
{
    NotificationManager mNotifyManager;

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {

        //Create a notification channel
        createNotificationChannel();

        //Setting up the notification content intent to launch the app when clicked in the notification bar
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job ran to completion!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());

        //Returning false because all work is completed in onStartJob() and not on another thread
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {
        //Return true so if the job fails the job can be rescheduled and not thrown out
        return true;
    }


    public void createNotificationChannel()
    {
        //Defining our notification manager
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Creating our notification channel
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Job Service Notification", NotificationManager.IMPORTANCE_HIGH);

        //Parameters set for the channel
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notifications from Job Service");

        mNotifyManager.createNotificationChannel(notificationChannel);
    }

    public class SimpleAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            //Sleep for 5 seconds
            int s = 5 * 1000;
            try{
                Thread.sleep(s);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            //Don't need to return anything
            return null;
        }
    }
}






/*                      From Lesson Notes
* About the onStartJob() callback:
    * Called when the system determines that your task should be run. In this method, you implement the job to be done.
    * Returns a boolean indicating whether the job needs to continue on a separate thread. If true, the work is offloaded
      to a different thread, and your app must call jobFinished() explicitly in that thread to indicate that the job is complete.
      If false, the system knows that the job is completed by the end of onStartJob(), and the system calls jobFinished() on your behalf.

*About the onStopJob() callback:

    * If the conditions described in the JobInfo are no longer met, the job must be stopped, and the system calls onStopJob().
    * The onStopJob() callback returns a boolean that determines what to do if the job is not finished. If the return value
      is, true, the job is rescheduled; otherwise, the job is dropped.
*/
