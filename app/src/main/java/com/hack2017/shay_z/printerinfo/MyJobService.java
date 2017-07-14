package com.hack2017.shay_z.printerinfo;

import android.app.Service;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by shay_z on 14-Jul-17.
 */

public class MyJobService extends JobService {
    private String TAG = "jobdispatcher";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob:!!!!!!!!!!!!!!!!!!!! ");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
