package com.hack2017.shay_z.printerinfo;

import android.util.Log;

import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shay_z on 19-Jun-17.
 */

public class UniversityHelper {


    public static ArrayList<University> getData(String dropBoxLink) throws Exception {
        ArrayList<University> universities = new ArrayList<>();
        String stringFromUrl = null;
        if (UrlUtils.getStringFromUrl(dropBoxLink) != null) {
            stringFromUrl = UrlUtils.getStringFromUrl(dropBoxLink);
            Log.d("123", "string from url 27");
            Log.d("123", stringFromUrl.toString());
        } else {
            Log.d("123", "UrlUtils.getStringFromUrl(dropBoxLink) is  null!!! on university helper 28");
        }

        Log.d("123", "oded   " + dropBoxLink);


        JSONObject parentJson;

        parentJson = new JSONObject(stringFromUrl);
        JSONArray parentArray = parentJson.getJSONArray("University"); // array of universities from dropbox file.
        Log.d("123", "85-DropBoxDataBase-parent array is =" + parentArray.toString());

        for (int i = 0; i < parentArray.length(); i++) {  // iter on parent array(universities array from dropbox file.)
            Log.d("123", "CURRENT PARENT ARRAY IS  = " + parentArray.get(i));
            universities.add(new University(parentArray.getJSONObject(i).getString("url"), parentArray.getJSONObject(i).getString("name"), parentArray.getJSONObject(i).getString("logo")));
//            String uName = universities.get(i).getName();
//            String uLogoUrl = universities.get(i).getLogoUrl();
//            universities.get(i).setImagePath( // set path ++ save logo !!
//                    UrlUtils.saveImage(mainActivity, uName, uLogoUrl)
//            );
            if (universities.get(i).getUrl() != null) {
                Log.d("123", "inside IF STATMENT  = ");
//                    universities.get(i).table = new MyJsoup(mainActivity).getTable(universities.get(i).getUrl()); // TODO: 14/06/2017
            } else {
                Log.e("123", "universities.get(i).getTable IS NULLLLLLLLLLL!!!!!!");

            }
        }


        return universities;

    }
}
