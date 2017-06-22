package com.hack2017.shay_z.printerinfo;

import android.os.AsyncTask;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class DropBoxDataBase extends AsyncTask<String, Integer, ArrayList<University>> {

    private static final String SAVE_UNIVERSITIES = "123";
    private final String dropBoxLink;
    private final MainActivity mainActivity;
    public ArrayList<University> universities = new ArrayList<>();
    ExeptionInterface exeptionInterface;

    public DropBoxDataBase(String dropBoxLink, MainActivity mainActivity) {
        this.dropBoxLink = dropBoxLink;
        exeptionInterface = (ExeptionInterface) mainActivity;
        this.mainActivity = mainActivity;
        execute(dropBoxLink);
    }

    void exp(Exception e) {
        exeptionInterface.onExceptionCallBack(e.getMessage());
    }

    @Override
    protected ArrayList<University> doInBackground(String... params) {
        try {
            return UniversityHelper.getData(params[0]);
        } catch (Exception e) {
            exeptionInterface.onExceptionCallBack(e.getMessage());
            return null;
        }
    }


    @Override
    protected void onPostExecute(ArrayList<University> universities) {
//        Log.d("123", "size" + universities.size());


        Log.d("123", " 96 universities array is " + universities.toString());
        if (universities != null)
            try {
                mainActivity.getDropBoxDatbase(universities);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}

