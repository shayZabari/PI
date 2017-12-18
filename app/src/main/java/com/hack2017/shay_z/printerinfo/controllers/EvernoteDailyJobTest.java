package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.hack2017.shay_z.printerinfo.models.MyEvernoteService;

import java.util.concurrent.TimeUnit;

/**
 * Created by Toshiba on 17/12/2017.
 */

public class EvernoteDailyJobTest extends DailyJob {
    public static final String DailyJobTag = "DailyJobTaggg";

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(Params params) {
        Log.d("123", "onRunDailyJob: 24 daily job onrundailyjob");
        Intent intent = new Intent(getContext(), MyEvernoteService.class);
        Log.d("123", "MY LOG !!--class= MyEvernoteJob: method=onRunJob:  before startservice");
        getContext().startService(intent);

        Log.d("123", "evernote success !!!!" + params.getTag());
        return DailyJobResult.SUCCESS;

    }

    public static void schedule(int hourChoosed, int minuteChoosed) {
//        if (JobManager.instance().getAllJobRequestsForTag(DailyJobTag).isEmpty()) {
//            // job already scheduled, nothing to do
//            Log.d("123", "schedule: dailyJob already scheduled");
//            return;
//        }
        JobRequest.Builder builder = new JobRequest.Builder(DailyJobTag);
        DailyJob.schedule(builder, TimeUnit.HOURS.toMillis(hourChoosed) + TimeUnit.MINUTES.toMillis(minuteChoosed)
                , TimeUnit.HOURS.toMillis(hourChoosed+1)+TimeUnit.MINUTES.toMillis(minuteChoosed));
        Log.d("123", "schedule: dailyblabla");

    }

}
