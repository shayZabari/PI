package com.hack2017.shay_z.printerinfo.models;

import android.os.AsyncTask;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.UniversityHelper;
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
    MainActivity mainActivity;

    public DatabaseTable(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    protected University doInBackground(University... universities) {
//
        University mUniversity = UniversityHelper.getTableDatabase(mainActivity, universities[0]);
        if (mUniversity != null) {
            return mUniversity;

        } else {
            Log.e(TAG, "doInBackground: 40");
            return null;
        }
    }


    @Override
    protected void onPostExecute(University university) {
        Log.d("123", university.toString());
        onDataBaseTableReceivedListener = (OnDataBaseTableReceivedListener) mainActivity;
        onDataBaseTableReceivedListener.onDataBaseTableReceived(university);
    }


    public interface OnDataBaseTableReceivedListener {
        void onDataBaseTableReceived(University university);
    }

}
