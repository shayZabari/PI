package com.hack2017.shay_z.printerinfo;

import android.os.AsyncTask;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;
import com.hack2017.shay_z.printerinfo.models.MyJsoup;
import com.hack2017.shay_z.printerinfo.models.University;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
        String stringFromUrl = null;
        Log.d("123", "oded   " + dropBoxLink);
        Log.d(("a"), "params 0 is = " + params[0]);
        try {
            if (UrlUtils.getStringFromUrl(params[0]) != null)
                stringFromUrl = UrlUtils.getStringFromUrl(params[0]);
            else
                return null;
        } catch (NullPointerException e) {
            Log.d("123", "59 null pointer exception" + e.getMessage());
            return null;

        } catch (IOException e) {
            try {
                throw (new IOException());
            } catch (IOException e1) {
//                Log.e("123", "64 IOexception" + e.printStackTrace(););

                Log.getStackTraceString(e);
                exeptionInterface.onExceptionCallBack(e.getMessage());
//                Logger.
//                Logger.Log(Level.INFO, e.getMessage(),e);
//                Arrays.toString(e.getStackTrace());
            }
        } catch (Exception e) {
            Log.d("123", "66 Exception11" + e.getMessage());
        }
        JSONObject parentJson;
        try {


            parentJson = new JSONObject(stringFromUrl);
            JSONArray parentArray = parentJson.getJSONArray("University"); // array of universities from dropbox file.
            Log.d("123", "85-DropBoxDataBase-parent array is =" + parentArray.toString());

            for (int i = 0; i < parentArray.length(); i++) {  // iter on parent array(universities array from dropbox file.)
                Log.d("123", "CURRENT PARENT ARRAY IS  = " + parentArray.get(i));
                universities.add(new University(parentArray.getJSONObject(i).getString("url"), parentArray.getJSONObject(i).getString("name"), parentArray.getJSONObject(i).getString("logo")));
                String uName = universities.get(i).getName();
                String uLogoUrl = universities.get(i).getLogoUrl();
                universities.get(i).setImagePath( // set path ++ save logo !!
                        UrlUtils.saveImage(mainActivity, uName, uLogoUrl)
                );
                if (universities.get(i).getUrl() != null) {
                    Log.d("123", "inside IF STATMENT  = ");
                    universities.get(i).table = new MyJsoup(mainActivity).getTable(universities.get(i).getUrl());
                } else {
                    Log.e("123", "universities.get(i).getTable IS NULLLLLLLLLLL!!!!!!");

                }
            }

        } catch (JSONException e) {
            Log.e("123", "92 DropBoxDataBase-try" + e.getMessage());
            exeptionInterface.onExceptionCallBack("92 DROP BOX DATA BASE" + e.getMessage());
        } catch (Exception e) {
            Log.e("123", "94 DROP BOX DATA BASE" + e.getMessage());
            exeptionInterface.onExceptionCallBack("94 DROP BOX DATA BASE" + e.getMessage());
        }

        return universities;
    }


    @Override
    protected void onPostExecute(ArrayList<University> universities) {
//        Log.d("123", "size" + universities.size());


        Log.d("123", " 96 universities array is " + universities.toString());

        mainActivity.getUniversityDataBase(universities);


    }
}

