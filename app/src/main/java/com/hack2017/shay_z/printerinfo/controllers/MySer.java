package com.hack2017.shay_z.printerinfo.controllers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shay_z on 21/06/2017.
 */

public class MySer extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("123", "on bindddddddddddddddddd");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service startede", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "service stoped", Toast.LENGTH_SHORT).show();
    }
}
