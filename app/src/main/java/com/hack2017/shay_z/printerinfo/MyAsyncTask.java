package com.hack2017.shay_z.printerinfo;

import android.os.AsyncTask;
import android.widget.ListView;

import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class MyAsyncTask extends AsyncTask<String, Integer, ArrayList<University>> {

    private ListView listView;

    public MyAsyncTask(ListView listView) {
        this.listView = listView;
    }

    protected ArrayList<University> doInBackground(String... params) {

        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<University> universities) {

    }
}
