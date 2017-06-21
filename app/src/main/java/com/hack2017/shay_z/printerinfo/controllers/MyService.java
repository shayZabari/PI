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

public class MyService extends IntentService {

    private MainActivity mainActivity;

    public MyService() {
        super("constructor MyService");
    }

    public MyService(MainActivity mainActivity) {
        super("constructor with context");
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("timer", "timer service had started");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        for (int i = 0; i < 3; i++) {
            Log.v("timer", "  = " + i + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            ArrayList<University> data = UniversityHelper.getData("https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0");
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
