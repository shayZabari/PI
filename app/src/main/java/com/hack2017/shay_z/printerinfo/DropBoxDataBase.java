package com.hack2017.shay_z.printerinfo;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;
import com.hack2017.shay_z.printerinfo.models.MyJsoup;
import com.hack2017.shay_z.printerinfo.models.University;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class DropBoxDataBase extends AsyncTask<String, Integer, ArrayList<University>> {
    private static final String SAVE_UNIVERSITIES = "123";
    private MainActivity mainActivity;
    private final String dropBoxLink;
    public ArrayList<University> universities = new ArrayList<>();
    ExeptionInterface exeptionInterface;

    public DropBoxDataBase(String dropBoxLink, MainActivity mainActivity) {

        this.dropBoxLink = dropBoxLink;
        exeptionInterface = mainActivity;

        execute(dropBoxLink);

    }

//    public void getDropboxUrl(String url) throws ExecutionException, InterruptedException {
//        new GetJsonData().execute(url).get();
//    }

    void exp(Exception e) {
        exeptionInterface.onExceptionCallBack(e.getMessage());
    }

    @Override
    protected ArrayList<University> doInBackground(String... params) {

        String stringFromUrl = null;

        Log.d("a", "oded   " + dropBoxLink);
        Log.d(("a"), "params 0 is = " + params[0]);

        try {
            stringFromUrl = UrlUtils.getStringFromUrl(params[0]);
        } catch (NullPointerException e) {
            Log.d("a", "59 null pointer exception" + e.getMessage());
            return null;

        } catch (IOException e) {
            try {
                throw (new IOException());
            } catch (IOException e1) {
//                Log.e("a", "64 IOexception" + e.printStackTrace(););

                Log.getStackTraceString(e);
                exeptionInterface.onExceptionCallBack(e.getMessage());
//                Logger.
//                Logger.Log(Level.INFO, e.getMessage(),e);
//                Arrays.toString(e.getStackTrace());
            }

        } catch (Exception e) {
            Log.d("a", "66 Exception11" + e.getMessage());


        }


        JSONObject parentJson = null;
        try {

            parentJson = new JSONObject(stringFromUrl);
            JSONArray parentArray = parentJson.getJSONArray("University"); // array of universities from dropbox file.
            for (int i = 0; i < parentArray.length(); i++) {  // iter on parent array(universities array from dropbox file.)
                Log.d("a", "CURRENT PARENT ARRAY IS  = " + parentArray.get(i));
                universities.add(new University(parentArray.getJSONObject(i).getString("url"), parentArray.getJSONObject(i).getString("name"), parentArray.getJSONObject(i).getString("logo")));
                if (universities.get(i).getUrl() != null) {
                    Log.d("a", "inside IF STATMENT  = ");
                    universities.get(i).table = new MyJsoup(mainActivity).getUrl(universities.get(i).getUrl());
                } else {
                    Log.e("a", "universities.get(i).getUrl IS NULLLLLLLLLLL!!!!!!");

                }
            }

        } catch (JSONException e1) {
            Log.e("a", "333");
        }


        return universities;
    }


    @Override
    protected void onPostExecute(ArrayList<University> universities) {
//        Log.d("a", "size" + universities.size());

        if (universities == null) {

            Log.d("a", " 96 University is null");
            return;
        }
        mainActivity.getUniversityDataBase(universities);
//        MyCustomAdapter adapter = new MyCustomAdapter(universities, mainActivity);
//        Log.d("a", universities + "");
    }
    //    class GetJsonData extends AsyncTask<String, String, ArrayList<University>> {
//
//        @Override
//        protected ArrayList<University> doInBackground(String... params) {
//
//            HttpURLConnection connection = null;
//            universities = new ArrayList<>();
//            BufferedReader reader;
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream stream = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(stream));
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                String finalJson = buffer.toString();
//                JSONObject parentJson = new JSONObject(finalJson);
//                JSONArray parentArray = parentJson.getJSONArray("University");
//
//                for (int i = 0; i < parentArray.length(); i++) {
//                    universities.add(new University(parentArray.getJSONObject(i).getString("url"), parentArray.getJSONObject(i).getString("name")));
//
//                }
//
//
//                    return universities;
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//
//            }
//            return null;
//        }
//
//
//    }
}

