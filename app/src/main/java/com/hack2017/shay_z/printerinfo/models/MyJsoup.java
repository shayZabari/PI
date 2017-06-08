package com.hack2017.shay_z.printerinfo.models;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by shay_z on 28-Apr-17.
 */

public class MyJsoup {

    private final Context context;
    public HashMap<String, String> hashMapIndexTable = new HashMap<>(); //   INDEX TABLE example "00ff11","no connection"
    public ArrayList<LineInTable> lines = new ArrayList<>();// PRINTER LINES INCLUDE HEX COLOR !
    //    public HashMap<String, Integer> statusesCountArr = new HashMap<>();
    Table table = new Table();
    ArrayList<StatusTable> statusTableArr = new ArrayList<>();
    ArrayList<Subject> subjects = new ArrayList<>();

    public MyJsoup(Context context) {
        this.context = context;
    }

    public Table getUrl(String url) throws Exception { // changed from <LineInTable> to <Table>..
        Log.d("123", "ENTERING getUrl in Jsoup !!!!!!!!!!!!!!!!");
        Document document = null;

        try {
            if (Jsoup.connect(url).get() != null) {
//                Jsoup.Connection.Request.validateTLSCertificates(boolean value)
//Jsoup.connect(url).request().validateTLSCertificates(true);
                document = Jsoup.connect(url).validateTLSCertificates(false).get();
            } else {
                Log.d("123", "IF ELSE JSOUP CNNOT CONNECT TO URL !!");
            }

        } catch (IOException e) {
            Log.d("123", "DOCUMENT-JSOUP canot connect to URL");
            e.printStackTrace();
        }

        //     **  INDEX TABLE  **   ( examples »  LineInTable OK / No answer / Non Active / Notification  /Alert / Non Rec. Msg


        try {
            assert document != null;
            Element indexTable = document.select("Table").get(0); // get index table
            Log.d("123", "indexTable is = " + indexTable.text());
            Elements indexTds = indexTable.getElementsByTag("Td"); // array of tds in index table
//            HashMap<String, String> indexTableHashMap;
//            indexTableHashMap = new HashMap<>();
            for (Element td : indexTds) {
                StatusTable tempStatusTable = new StatusTable();
                tempStatusTable.statusID = td.text().replace("\u00a0", "").trim();
                tempStatusTable.hexColor = td.attr("bgcolor").trim();
                Log.d(TAG, "getUrl: " + tempStatusTable.toString());
                statusTableArr.add(tempStatusTable);
                //            indexTableHashMap.put(td.attr("bgcolor").trim(), td.text().replace("\u00a0", "").trim()); // array of key value of td from table1
            }
//        hashMapIndexTable = indexTableHashMap;  // save index table <KEY=STATUS , VALUE=HEX COLOR>
        } catch (Exception e) {
            try {
                UrlUtils.addLog(context, e, e.toString());
            } catch (Exception e1) {
                Log.e("123", "76 MyJsoup"+e1.getMessage());
                e1.printStackTrace();
            }

        }
        return getPrinterTable(document);


    }

    private Table getPrinterTable(Document document) throws Exception{
        // **  PRINTERS TABLE **
        Element printersTable = null; // save all second table(printer table)
        try {
            printersTable = document.select("Table").get(1);

            Elements trs = null;// save all line in linesArrInPrintersTable
            try {
                trs = printersTable.getElementsByTag("tr");

        int c = 0;
        int count = 1;
        Elements tdsID = trs.get(0).getElementsByTag("td");
        for (int i = 0; i < tdsID.size(); i++) {
            Subject subject = new Subject();
            subject.name = tdsID.get(i).text().trim();
            subject.getPosition = i;
            subjects.add(subject);
            Log.d("123", "element tdID id " + tdsID.get(i).text().trim());


        }
            } catch (Exception e) {
                UrlUtils.addLog(context, e, e.toString());
                e.printStackTrace();
            }


        for (Element tr : trs) { // ITER ON TR printer table ***
            String bgColor = tr.getElementsByTag("td").get(0).attr("bgcolor").trim();// hex color
            LineInTable lineInTable = null;
//            for (String hexColor : hashMapIndexTable.keySet()) {  // ITER ON STATUS HEX COLOR ***
            for (StatusTable statusTable : statusTableArr) {
                if (statusTable.hexColor.equals(bgColor)) {
                    statusTable.count += 1;
//                    if (hexColor.equals(bgColor)) { //s=ff00ff td=ff00ff
//                    Log.d("123", "hex color= " + hexColor + " td color= " + tr.getElementsByTag("td").get(0).attr("bgcolor").trim());
//            Log.d("123", "count trs="+ c++ +" " +tr.text());

//                        String keyStatus = hashMapIndexTable.get(hexColor);
//                        Integer valueStatus = table.statusesCountArr.get(keyStatus);
//                        if (valueStatus == null) { // STATUS COUNTER
//                            Log.d("123", "null");
//                            table.statusesCountArr.put(keyStatus, count); // STATUS COUNTER
//                        } else {
//                            Log.d("123", "not null");
//                            table.statusesCountArr.put(keyStatus, valueStatus + 1);// STATUS COUNTER
//                        }
                    lineInTable = new LineInTable();  // NEW OBJECT OF LineInTable
                    Elements lineTDS = tr.getElementsByTag("td"); // ARRAY OF TD IN 1 ROW
                    for (Element td : lineTDS) { // ITER ON TD'S OF ONE ROW
                        lineInTable.status = statusTable.statusID;
                        lineInTable.hexColor = statusTable.hexColor;
                        lineInTable.stringOfAllTheLine.add(td.text().trim()); // add single td to strings in LineInTable
                    }

//                        lines.add(lineInTable); temp removed 0524 17:19
                    statusTable.allLinesOfStatus.add(lineInTable); // TODO: 24-May-17
                }
                table.statusTableArr = statusTableArr;
                table.subjects = subjects;
            }
        }

        // END OF ALL ITERS ***

//        table.lineInTableArrayList = lines; temp removed // TODO: 02-Jun-17
        } catch (Exception e) {
            try {
                Log.e("123", "155 MY JSOUP"+e.getMessage());
                UrlUtils.addLog(context, e, e.toString());
            } catch (Exception e1) {
                Log.e("123", "158 MY JSOUP" + e.getMessage()+"context"+context.toString());
            }

        }
        return table;
    }
}

