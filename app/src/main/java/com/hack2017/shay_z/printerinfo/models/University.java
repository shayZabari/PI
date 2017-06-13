package com.hack2017.shay_z.printerinfo.models;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.controllers.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class University implements Serializable {
    private final MainActivity mainActivity;
    private String url, name;
    //    public ArrayList<LineInTable> printerStatuses; // may need to delete after creating Table object :
    public Table table;
    Drawable logo;


    public Drawable getLogo() {
        return logo;
    }


    public University(MainActivity mainActivity, String url, String name, String logoUrl) {
        this.mainActivity = mainActivity;
        this.url = url;
        this.name = name;
        setDrawbleLogo(logoUrl);
        setBitMapLogo(logoUrl);
    }


    private String setBitMapLogo(String logoUrl) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = new URL(logoUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContextWrapper cw = new ContextWrapper(mainActivity);
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File myPath = new File(directory,name+".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
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
        return directory.getName();
    }


    private void setDrawbleLogo(String logoUrl) {
        InputStream is = null;
        try {
            is = (InputStream) new URL(logoUrl).getContent();
        } catch (IOException e) {
            Log.d("123", "Ioeceptionnnnn UniversityClass inputstream");
            e.printStackTrace();
        }
        this.logo = Drawable.createFromStream(is, "src name");
//        FileOutputStream fileOutputStream ;
//        try {
//            fileOutputStream = mainActivity.openFileOutput(name, Context.MODE_PRIVATE);
//            logo.
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
