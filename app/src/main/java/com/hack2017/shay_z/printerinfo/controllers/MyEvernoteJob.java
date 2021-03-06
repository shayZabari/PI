package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.hack2017.shay_z.printerinfo.models.MyEvernoteService;

/**
 * Created by Toshiba on 01/08/2017.
 * Usage
 The class JobManager serves as entry point. Your jobs need to extend the class Job
 */

class MyEvernoteJob extends Job {
    static final String JOB_TAG = "demo_job";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        Intent intent = new Intent(getContext(), MyEvernoteService.class);
        Log.d("123", "MY LOG !!--class= MyEvernoteJob: method=onRunJob:  before startservice");
        getContext().startService(intent);

        Log.d("123", "evernote success !!!!" + params.getTag());
        return Result.SUCCESS;
    }

}
