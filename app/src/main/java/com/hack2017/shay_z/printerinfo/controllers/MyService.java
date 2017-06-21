package com.hack2017.shay_z.printerinfo.controllers;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.UniversityHelper;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * Created by shay_z on 16-Jun-17.
 */

public class MyService extends Service {



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

//    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            Log.d("123", "on hangle in tent in service !!!!!");
        for (int i = 0; i < 3; i++) {
            Log.d("123", "  = " + i + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            ArrayList<University> data = UniversityHelper.getData("https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0");
                Log.d("123", "on handle sleep 10 sec");
            for (int i = 0; i < 100; i++) {
                Thread.sleep(10000);
                Log.d("123", "myservice ! = " + "counter is " + i + " " + data.get(0).getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mainActivity.testService();
    }
}
