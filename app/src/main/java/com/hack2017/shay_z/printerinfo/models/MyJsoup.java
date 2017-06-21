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

public class MyJsoup extends AsyncTask<University, Object, University> {

    private final MainActivity mainActivity;
    Table table = new Table();
    ArrayList<StatusTable> statusTableArr = new ArrayList<>();
    ArrayList<Subject> subjects = new ArrayList<>();
    private String url;
    private University currentUniversity;

    public MyJsoup(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public Table getTable(String url) throws Exception {
        this.url = url;
        Document document = getDocument(url);
        table.statusTableArr = (getStatusTableArr(document));
        Elements trsInPrintersTable = getTrsFromPrintersTable(document);
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
        table.subjects = getSubjects(trsInPrintersTable);
        return table;
    }

    public Document getDocument(String url) {

        try {
            if (Jsoup.connect(url).get() != null) {

                return Jsoup.connect(url).get();
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("123", "41 myjsoup" + e.getMessage());
        }
        return null;
    }

    public ArrayList<StatusTable> getStatusTableArr(Document document) {
        //  **  STATUS TABLE  **   ( examples »  LineInTable OK / No answer / Non Active / Notification  /Alert / Non Rec. Msg
        Log.d("123", "ENTERING STATUS TABLE");

        try {
            assert document != null;
            Element indexTable = document.select("table").get(0); // get index table
            Elements indexTds = indexTable.getElementsByTag("Td"); // array of tds in index table

            for (Element td : indexTds) {
                StatusTable tempStatusTable = new StatusTable();
                tempStatusTable.statusID = td.text().replace("\u00a0", "").trim();
                tempStatusTable.hexColor = td.attr("bgcolor").trim();
                statusTableArr.add(tempStatusTable);
            }
        } catch (Exception e) {
            try {
//                UrlUtils.addLog(context, e, e.toString());
                Log.e("123", "77-MyJsoup -" + e.getMessage());
            } catch (Exception e1) {
                Log.e("123", "76 MyJsoup" + e1.getMessage());
                e1.printStackTrace();
            }
        }
        return statusTableArr;
    }

    private Elements getTrsFromPrintersTable(Document document) {
        Log.d("123", "ENTERING PRINTERS TABLE");
        Element printersTable = null;
        printersTable = document.select("table").get(1);
//        Log.d("123", "94 MyJsoup - printersTable" + printersTable);
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
        return trsGood;
    }


    //get subjects from printer table (ip/location/ etc..)
    public ArrayList<Subject> getSubjects(Elements trsInPrintersTable) throws Exception {
        Elements tds = trsInPrintersTable.get(0).getElementsByTag("td");
        boolean firstTime = false;
        if (currentUniversity.table == null) {
            firstTime = true;
        }
        Subject subject;
        ArrayList<Subject> tempSubjects = UrlUtils.spLoadCheckboxes(mainActivity, currentUniversity);
        for (int i = 0; i < tds.size(); i++) {
            subject = new Subject();
            subject.name = tds.get(i).text().trim();
            subject.getPosition = i;
            if (!firstTime && tempSubjects != null) {
                if (tempSubjects.size() > 0)
                    subject.checkBoxStatus = UrlUtils.spLoadCheckboxes(mainActivity, currentUniversity).get(i).checkBoxStatus;
                subject.getPosition = UrlUtils.spLoadCheckboxes(mainActivity, currentUniversity).get(i).getPosition;
            }
            subjects.add(subject);
        }
        return subjects;
    }

    @Override
    protected University doInBackground(University... universities) {
        currentUniversity = universities[0];
        this.url = currentUniversity.getUrl();
        Log.d(TAG, "doInBackground: 152");
        Document document = getDocument(url);
        Log.d(TAG, "doInBackground: 154");
        table.statusTableArr = (getStatusTableArr(document));
        Log.d(TAG, "doInBackground: 154");
        Elements trsInPrintersTable = getTrsFromPrintersTable(document);
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
            table.subjects = getSubjects(trsInPrintersTable);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: 179", e);
            e.printStackTrace();
        }
        Log.d(TAG, "doInBackground: 178");
        currentUniversity.table = table;
        return currentUniversity;

    }

    @Override
    protected void onPostExecute(University university) {
        Log.d("123", university.toString());
        mainActivity.onTableFinished(university);
    }
}

