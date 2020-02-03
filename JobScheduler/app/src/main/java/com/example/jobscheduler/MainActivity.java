package com.example.jobscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    RadioGroup networkOptions;
    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;

    //Switches for setting job options
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;

    private SeekBar mSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);

        mSeekBar = findViewById(R.id.seekBar);
        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                //If i is greater than zero than the progress bar has been set to a value
                if(i > 0) seekBarProgress.setText(i + "s");

                else seekBarProgress.setText("Not Set");
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void scheduleJob(View view)
    {
        networkOptions = findViewById(R.id.networkOptions);

        //Initializing our scheduler
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        //Retrieving the selected network ID (None, Any, Wifi)
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();

        //setting the default network option
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        switch(selectedNetworkID)
        {
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        //Identifying the specific application service we want
        ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());

        //Checking if the SeekBar has been set to a value as a constraint
        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;

        boolean constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE) || mDeviceChargingSwitch.isChecked() || mDeviceIdleSwitch.isChecked() || seekBarSet;

        if(constraintSet)
        {
            //Building out job with its specific ID and the service
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                    .setRequiredNetworkType(selectedNetworkOption)
                    .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked())
                    .setRequiresCharging(mDeviceChargingSwitch.isChecked());

            //if a there is a seek bar value than set it
            if(seekBarSet)
                builder.setOverrideDeadline(seekBarInteger * 1000);

            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
        }
        else
            Toast.makeText(this, "Job Scheduled, job will run when " + "the constraints are met.", Toast.LENGTH_SHORT).show();

    }

    public void cancelJobs(View view)
    {

        //If the scheduler is not null then we need to cancel it once it is over
        if(mScheduler != null)
        {
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
