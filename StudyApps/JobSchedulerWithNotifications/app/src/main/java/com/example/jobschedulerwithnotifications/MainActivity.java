package com.example.jobschedulerwithnotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    //ID for the notification job
    private static final int JOB_ID = 0;

    //JobScheduler Object
    private JobScheduler mScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }


    public void scheduleJob(View view)
    {
        //ComponentName acts as an identifier for a specific Activity, Service, BroadcastReceiver, or ContentProvider
        ComponentName serviceName = new ComponentName(getPackageName(), NotificationService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName).setRequiresCharging(true);
        JobInfo myJobInfo = builder.build();
        mScheduler.schedule(myJobInfo);
        Toast.makeText(this, "Notification Scheduled in Background", Toast.LENGTH_SHORT).show();
    }
}
