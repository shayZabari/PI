package com.hack2017.shay_z.printerinfo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.util.TimeUtils;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.Table;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shay_z on 14-Jul-17.
 */

public class MyJobService extends JobService {
    NotificationManagerCompat mgr;
    private String TAG = "jobdispatcher";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob:!!!!!!!!!!!!!!!!!!!! ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                push();
            }
        }).start();

        return false;
    }

    private void push() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("time", new Date().toString());
        University currentUniversity = UrlUtils.spLoadUniversities(getApplicationContext()).get(UrlUtils.spLoadUniversityPosition(getApplicationContext()));
        StringBuilder stringBuilder = new StringBuilder();
        Table t = new Table();
        t.statusTableArr = UniversityHelper.getTableDatabase(getApplicationContext(), currentUniversity).table.statusTableArr;
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(getApplicationContext()).load(currentUniversity.getLogoUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (StatusTable statusTable : t.statusTableArr) {
            String temp = statusTable.statusID.substring(0, 3);
            stringBuilder.append(temp + "=");
            stringBuilder.append(statusTable.count + " ");

        }

        String ss = UniversityHelper.getTableDatabase(getApplicationContext(), currentUniversity).table.statusTableArr.get(0).statusID;

//        String s = currentUniversity.table.statusTableArr.get(0).toString();
        int rcPending = 2;
        PendingIntent pi = PendingIntent.getActivity(this, rcPending, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("PI info updated:" + new Date().toString())
                .setContentText(stringBuilder)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap);

        Notification notification = builder.build();
        mgr = NotificationManagerCompat.from(this);
        Log.d(TAG, "push: notification in service" + Calendar.getInstance().get(Calendar.DATE));
        mgr.notify(1, notification);

    }

    @Override
    public boolean onStopJob(JobParameters job) {
        mgr.cancel(1);
        return false;
    }
}
