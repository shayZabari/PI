package com.hack2017.shay_z.printerinfo.models;

import android.os.AsyncTask;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.controllers.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * Created by shay_z on 28-Apr-17.
 */

public class DatabaseTable extends AsyncTask<University, Object, University> {
    public OnDataBaseTableReceivedListener onDataBaseTableReceivedListener;


    @Override
    protected University doInBackground(University... universities) {
        return getUniversity(universities);

    }


    @Override
    protected void onPostExecute(University university) {
        Log.d("123", university.toString());
        onDataBaseTableReceivedListener.onDataBaseTableReceived(university);
    }


}

interface OnDataBaseTableReceivedListener {
    void onDataBaseTableReceived(University university);
}
