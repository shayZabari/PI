package com.hack2017.shay_z.printerinfo.controllers;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by Toshiba on 01/08/2017.
 */

class AdeptAndroidJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        return new MyEvernoteJob();
    }
}
