package com.hack2017.shay_z.printerinfo;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.hack2017.shay_z.printerinfo.models.LineInTable;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.Subject;
import com.hack2017.shay_z.printerinfo.models.Table;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by shay_z on 19-Jun-17.
 */

public class UniversityHelper {


    public static ArrayList<University> getDropboxData(String dropBoxLink) throws Exception {
        ArrayList<University> universities = new ArrayList<>();
        String stringFromUrl = null;
        if (UrlUtils.getStringFromUrl(dropBoxLink) != null) {
            stringFromUrl = UrlUtils.getStringFromUrl(dropBoxLink);
            Log.d("123", "string from url 27");
            Log.d("123", stringFromUrl.toString());
        } else {
            Log.d("123", "UrlUtils.getStringFromUrl(dropBoxLink) is  null!!! on university helper 28");
        }
        JSONObject parentJson;
        parentJson = new JSONObject(stringFromUrl);
        JSONArray parentArray = parentJson.getJSONArray("University"); // array of universities from dropbox file.
        Log.d("123", "85-DatabaseDropbox-parent array is =" + parentArray.toString());


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
//                    universities.get(i).table = new DatabaseTable(mainActivity).getTable(universities.get(i).getUrl()); // TODO: 14/06/2017
            } else {
                Log.e("123", "universities.get(i).getTable IS NULLLLLLLLLLL!!!!!!");

            }
        }


        return universities;

    }

    public static University getTableDatabase(Context context, University university) {

        Table table = new Table();
        ArrayList<StatusTable> statusTableArr = new ArrayList<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        String url;
        University currentUniversity;
        currentUniversity = university;
        Document document = null;
        {
            url = currentUniversity.getUrl();
            Log.d(TAG, "doInBackground: 152");

            try {
                if (Jsoup.connect(url).get() != null) {

                    document = Jsoup.connect(url).get();
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.e("123", "41 myjsoup" + e.getMessage());
            }


            Log.d(TAG, "doInBackground: 154");

            try {
                Element indexTable = document.select("table").get(0); // get index table
                Elements indexTds = indexTable.getElementsByTag("Td"); // array of tds in index table

                for (Element td : indexTds) {
                    StatusTable tempStatusTable = new StatusTable();
                    tempStatusTable.statusID = td.text().replace("\u00a0", "").trim();
                    tempStatusTable.hexColor = td.attr("bgcolor").trim();
                    statusTableArr.add(tempStatusTable);
                }
                table.statusTableArr = statusTableArr;
            } catch (Exception e) {
                try {
//                UrlUtils.addLog(context, e, e.toString());
                    Log.e("123", "77-DatabaseTable -" + e.getMessage());
                } catch (Exception e1) {
                    Log.e("123", "76 DatabaseTable" + e1.getMessage());
                    e1.printStackTrace();
                }
            }


            Log.d(TAG, "doInBackground: 154");
            Elements trsInPrintersTable;
            Log.d("123", "ENTERING PRINTERS TABLE");
            Element printersTable = null;
            printersTable = document.select("table").get(1);
//        Log.d("123", "94 DatabaseTable - printersTable" + printersTable);
            Elements trs = null;// save all line in linesArrInPrintersTable
            trs = printersTable.getElementsByTag("tr"); // array of tr in printers table !
            Elements trsGood = new Elements();
            for (Element tr : trs) {
                if (tr.select("td").size() > 0) {

                    trsGood.add(tr);
//                Log.d("123", "114 myjsoup");
                } else {
                    Log.d("123", "no hasattr");
                }
            }
            trsInPrintersTable = trsGood;


            Log.d(TAG, "doInBackground: 157");
            for (Element tr : trsInPrintersTable) {
                String bgColor = tr.getElementsByTag("td").get(0).attr("bgcolor").trim();
                LineInTable lineInTable = null;
                for (StatusTable statusTable : table.statusTableArr) {
                    if (statusTable.hexColor.equals(bgColor)) { //if current status equal current printerTable bgcolor
                        statusTable.count += 1;
                        lineInTable = new LineInTable();  // NEW OBJECT OF LineInTable
                        Elements lineTDS = tr.getElementsByTag("td"); // ARRAY OF TD IN CURRENT TR
                        for (Element td : lineTDS) { // ITER ON TD'S OF CURRENT ROW
                            lineInTable.status = statusTable.statusID;
                            lineInTable.hexColor = statusTable.hexColor;
                            lineInTable.stringOfAllTheLine.add(td.text().trim()); // add single td to strings in LineInTable //need ???
                        }
                        statusTable.allLinesOfStatus.add(lineInTable);
                    }
                }
            }
            Log.d(TAG, "doInBackground: 176");
            try {

                Elements tds = trsInPrintersTable.get(0).getElementsByTag("td");
                boolean firstTime = false;
                if (currentUniversity.table == null) {
                    firstTime = true;
                }
                Subject subject;
                ArrayList<Subject> tempSubjects = UrlUtils.spLoadCheckboxes(context, currentUniversity);
                for (int i = 0; i < tds.size(); i++) {
                    subject = new Subject();
                    subject.name = tds.get(i).text().trim();
                    subject.getPosition = i;
                    if (!firstTime && tempSubjects != null) {
                        if (tempSubjects.size() > 0)
                            subject.checkBoxStatus = UrlUtils.spLoadCheckboxes(context, currentUniversity).get(i).checkBoxStatus;
                        subject.getPosition = UrlUtils.spLoadCheckboxes(context, currentUniversity).get(i).getPosition;
                    }
                    subjects.add(subject);
                }
                table.subjects = subjects;


            } catch (Exception e) {
                Log.e(TAG, "doInBackground: 179", e);
                e.printStackTrace();
            }
            Log.d(TAG, "doInBackground: 178");
            currentUniversity.table = table;
            return currentUniversity;
        }


    }
}
