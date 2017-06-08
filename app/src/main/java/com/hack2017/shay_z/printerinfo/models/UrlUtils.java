package com.hack2017.shay_z.printerinfo.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hack2017.shay_z.printerinfo.controllers.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.id.message;

/**
 * Created by shay_z on 10-May-17.
 */

public class UrlUtils {
    private MainActivity activity;

    public static String addLog(Context context, Exception e, String problem) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss \n");
//        String currentDateandTime = sdf.format(new Date());

        SharedPreferences sp = context.getSharedPreferences("log.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("loge",currentDateandTime).commit();
        edit.putString("loge", sw.toString()).commit();

        Log.e("a", sw.toString());
        return sw.toString();
    }

    public static String getLog(Context context) {
        SharedPreferences sp = context.getSharedPreferences("log.txt", Context.MODE_PRIVATE);
        return sp.getString("loge", "no records");
    }

    public static String getStringFromUrl(String str) throws Exception {
        URL url = null;
            String finalJson = null;
//        try {
            url = new URL(str);
            HttpURLConnection connection = null;
            BufferedReader reader;

            finalJson = getString(url, finalJson);
            Log.e("a", "64 ERROR IN URL UTILS/GETSTRINGFROMURL");
//        } catch (MalformedURLException e) {
//            Log.e("a", "66 SHAY ERROR IN URL UTILS"+e.getMessage());
//
//        } catch (IOException e) {
//            Log.e("a", "70 SHAY ERROR IN URL UTILS"+e.getMessage());
//        }


        return finalJson;
    }

    @NonNull
    private static String getString(URL url, String finalJson) throws IOException {
        HttpURLConnection connection;
        BufferedReader reader;
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        finalJson = buffer.toString();
        return finalJson;
    }

    public void addMainActivity(MainActivity activity) {
        this.activity = activity;
    }
}
