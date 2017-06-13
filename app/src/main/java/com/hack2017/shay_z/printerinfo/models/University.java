package com.hack2017.shay_z.printerinfo.models;


import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class University implements Serializable {
    private String url, name;
    //    public ArrayList<LineInTable> printerStatuses; // may need to delete after creating Table object :
    public Table table;
    Drawable logo;


    public Drawable getLogo() {
        return logo;
    }


    public University(String url, String name, String logoUrl) {
        this.url = url;
        setDrawbleLogo(logoUrl);
        this.name = name;
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
