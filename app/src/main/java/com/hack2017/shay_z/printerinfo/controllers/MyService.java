package com.hack2017.shay_z.printerinfo.controllers;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.UniversityHelper;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * Created by shay_z on 16-Jun-17.
 */

public class MyService extends Service {
    private Intent intent;


//    public MyService() {
//        super("constructor MyService");
//    }


    @Override
    public void onCreate() {
        Log.d("123", "timer service had started");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("123", "on start command from service");
        onHandleIntent(intent);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("123", "start service OnBinde");
        return null;
    }


    public void onHandleIntent(@Nullable Intent intent) {
        this.intent = intent;
        Log.d("123", "on hangle in tent in service !!!!!");
        new DoBackgroudTask().execute();
//        for (int i = 0; i < 3; i++) {
//            Log.d("123", "  = " + i + "");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        mainActivity.testService();
    }
}

    public void setNotification() {
        Notification n = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
//                .setSmallIcon(R.drawable.icon)
//                .setContentIntent(pIntent)
                .setAutoCancel(true).build();
//                .addAction(R.drawable.icon, "Call", pIntent)
//                .addAction(R.drawable.icon, "More", pIntent)
//                .addAction(R.drawable.icon, "And more", pIntent).build();
    }


 class DoBackgroudTask extends AsyncTask<Object, Object, ArrayList<University>> {

    @Override
    protected ArrayList<University> doInBackground(Object... urls) {
        Log.d("123", "on do in background service !!!!!");
        try {
            ArrayList<University> universities = null;
            for (int i = 0; i < 50; i++) {
                universities = UniversityHelper.getData("https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0");
                Thread.sleep(2000);
                Log.d("123", "myservice ! = " + "counter is " + i + " " + universities.get(0).getName());

            }
            return universities;
        } catch (Exception e) {
            Log.d("123", "exeption is do in backgroud" + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {

        return null;
        }
    }

    //    @Override

}