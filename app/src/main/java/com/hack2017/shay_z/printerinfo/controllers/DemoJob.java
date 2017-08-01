package com.hack2017.shay_z.printerinfo.controllers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.hack2017.shay_z.printerinfo.MyJobService;

/**
 * Created by Toshiba on 01/08/2017.
 */

class DemoJob extends Job {
    static final String JOB_TAG = "demo_job";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        new MyJobService();
        Log.d("123", "evernote success !!!!" + params.getTag());
        return Result.SUCCESS;
    }
}
