package com.hack2017.shay_z.printerinfo.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hack2017.shay_z.printerinfo.controllers.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.name;
import static android.R.id.message;

/**
 * Created by shay_z on 10-May-17.
 */

public class UrlUtils {
    private static final String SAVE_UNIVERSITIES = "universities";
    private MainActivity activity;


    public static int spLoadUniversityPosition(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        return appSharedPrefs.getInt("universityPosition", -1); // in not exist then return -1
    }

    public static void spSaveUniversityPosition(Context context, int universityPosition) {
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = editor.edit();
        prefEditor.putInt("universityPosition", universityPosition);
        prefEditor.commit();

    }
    public static void spSaveCheckboxs(Context context, ArrayList<Subject> subjects, University university) throws Exception {
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = editor.edit();
        Gson gson = new Gson();
        String json = gson.toJson(subjects);

        String universityCheckboxes = university.getName() + "checkboxs";
        prefEditor.putString(universityCheckboxes, json);
        prefEditor.commit();
    }

    public static ArrayList<Subject> spLoadCheckboxes(Context context, University university) throws Exception {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();


        String universityCheckboxes = university.getName() + "checkboxs";
        String json = appSharedPrefs.getString(universityCheckboxes, "");
        Type type = new TypeToken<List<Subject>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public static void spSaveUniversities(Context context, ArrayList<University> universities) {
        // save preferences
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = editor.edit();
        Gson gson = new Gson();
        String json = gson.toJson(universities);

        Log.d("123", "json is "+universities);
        prefEditor.putString(SAVE_UNIVERSITIES,json );
        prefEditor.commit();
    }

    public static ArrayList<University> spLoadUniversities(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(SAVE_UNIVERSITIES, "");
        Type type = new TypeToken<List<University>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String addLog(Context context, Exception e, String problem)  {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss \n");
//        String currentDateandTime = sdf.format(new Date());
        Log.d("123", "40 CONTEXT IS ="+context.toString());
        SharedPreferences sp = context.getSharedPreferences("log.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("loge",currentDateandTime).commit();
//        edit.putString("loge", sw.toString()).commit();
//
//        Log.e("123", sw.toString());
        return e.getMessage();
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
//            Log.e("123", "64 ERROR IN URL UTILS/GETSTRINGFROMURL");
//        } catch (MalformedURLException e) {
//            Log.e("123", "66 SHAY ERROR IN URL UTILS"+e.getMessage());
//
//        } catch (IOException e) {
//            Log.e("123", "70 SHAY ERROR IN URL UTILS"+e.getMessage());
//        }


        return finalJson;
    }

    @NonNull
    private static String getString(URL url, String finalJson)  {
        HttpURLConnection connection;
        BufferedReader reader;
        InputStream stream = null;
        try {
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
            stream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finalJson = buffer.toString();
        return finalJson;
    }

    public void addMainActivity(MainActivity activity) {
        this.activity = activity;
    }

    public  static String saveImage(Context context, String name, String logoUrl) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = new URL(logoUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File myPathandimage = new File(directory, name +".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPathandimage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Log.d("123", "sssssssssssssssssssssssssssss"+directory.getAbsolutePath());

        return directory.getAbsolutePath();
    }

    public static Bitmap loadImage(String imagePath, String imageName) {
        File f = new File(imagePath, imageName + ".jpg");
        Bitmap bitmap = null;
        try {
            bitmap=BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
