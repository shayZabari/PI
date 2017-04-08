package com.hack2017.shay_z.printerinfo;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by shay_z on 07-Apr-17.
 */

public class University {
    ArrayList<PrinterLine> printerLines;
    URL url;
    String name;
String[] status;
    public University(String name, String url) {
        this.name = name;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            printerLines=new HttpString().execute(this.url).get();
            Log.d("aaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbb");
            //// TODO: 07-Apr-17  calculate status after asynctask finish 
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
