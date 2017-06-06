package com.hack2017.shay_z.printerinfo;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;
import com.hack2017.shay_z.printerinfo.models.MyJsoup;
import com.hack2017.shay_z.printerinfo.models.University;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class DropBoxDataBase extends AsyncTask<String, Integer, ArrayList<University>> {
    private static final String SAVE_UNIVERSITIES = "123";
    private final MainActivity mainActivity;
    private final String dropBoxLink;
    public ArrayList<University> universities = new ArrayList<>();


    public DropBoxDataBase(String dropBoxLink, MainActivity mainActivity) {

        this.dropBoxLink = dropBoxLink;
        this.mainActivity = mainActivity;

        execute(dropBoxLink);

    }

//    public void getDropboxUrl(String url) throws ExecutionException, InterruptedException {
//        new GetJsonData().execute(url).get();
//    }

    @Override
    protected ArrayList<University> doInBackground(String... params) {

        String stringFromUrl = null;

        try {
            stringFromUrl = UrlUtils.getStringFromUrl(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject parentJson = null;
        try {
            parentJson = new JSONObject(stringFromUrl);
            JSONArray parentArray = parentJson.getJSONArray("University"); // array of universities from dropbox file.
            for (int i = 0; i < parentArray.length(); i++) {  // iter on parent array(universities array from dropbox file.)
                Log.d("a", "CURRENT PARENT ARRAY IS  = " + parentArray.get(i));
                universities.add(new University(parentArray.getJSONObject(i).getString("url"), parentArray.getJSONObject(i).getString("name"), parentArray.getJSONObject(i).getString("logo")));
                if (universities.get(i).getUrl() != null) {
                Log.d("a", "inside IF STATMENT  = " );
                    universities.get(i).table = new MyJsoup(mainActivity).getUrl(universities.get(i).getUrl());
                } else {
                    Log.e("a", "universities.get(i).getUrl IS NULLLLLLLLLLL!!!!!!");

                }
            }

        } catch (JSONException e1) {
            Log.e("a", "333");
            e1.printStackTrace();
        }


        return universities;
    }



    @Override
    protected void onPostExecute(ArrayList<University> universities) {
        Log.d("a", "size" + universities.size());


        mainActivity.getUniversityDataBase(universities);
//        MyCustomAdapter adapter = new MyCustomAdapter(universities, mainActivity);
        Log.d("a", universities + "");
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

