package com.hack2017.shay_z.printerinfo.models;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.UniversityHelper;
import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shay_z on 01-Aug-17.
 */

public class MyEvernoteService extends Service {
    NotificationManagerCompat mgr;
    private String TAG = "jobdispatcher";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("123", "MY LOG !!--class= MyEvernoteService: method=onStartCommand:  ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                push();
            }
        }).start();
        return START_NOT_STICKY;
    }

    private void push() {
        Log.d("123", "MY LOG !!--class= MyEvernoteService: method=push:  ");
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
    public void onCreate() {
        super.onCreate();
        Log.d("123", "MyEvernoteService: onCreate:  ");
        Log.d("123", "class= MyEvernoteService: method=onCreate:  ");
        Log.d("123", "MY LOG !!--class= MyEvernoteService: method=onCreate:  ");
    }
}
