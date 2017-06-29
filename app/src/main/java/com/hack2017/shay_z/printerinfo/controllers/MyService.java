package com.hack2017.shay_z.printerinfo.controllers;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.UniversityHelper;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * Created by shay_z on 16-Jun-17.
 */

public class MyService extends Service {
    NotificationManager nm;
    private Intent intent;
    private NotificationCompat.Builder notification;
    private int ID = 123;


//    public MyService() {
//        super("constructor MyService");
//    }
University university;

    @Override
    public void onCreate() {
        Log.d("123", "timer service had started");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("123", "on start command from service");
        university = (University) intent.getSerializableExtra("uni");
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
//}

    public void setNotification(String name) {
        notification = new NotificationCompat.Builder(getBaseContext());
        notification.setAutoCancel(true);
        notification.setTicker(name);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentTitle("setcontenttitle" + name);
//        notification.setContentText("setcontentText"+name);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(ID, notification.build());
    }


    class DoBackgroudTask extends AsyncTask<Object, Object, ArrayList<University>> {

        @Override
        protected ArrayList<University> doInBackground(Object... urls) {
            Log.d("123", "on do in background service !!!!!");
            try {
                ArrayList<University> universities = null;
                for (int i = 0; i < 5; i++) {
//                    universities = UniversityHelper.getDropboxData("https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0");
                    University university1 = UniversityHelper.getTableDatabase(getBaseContext(), university);
                    setNotification(university1.getName());
                    Thread.sleep(4000);
                    nm.cancel(ID);
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
}