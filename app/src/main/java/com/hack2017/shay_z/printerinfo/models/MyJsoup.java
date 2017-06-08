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

    public Table getUrl(String url) { // changed from <LineInTable> to <Table>..
        Log.d("123", "ENTERING getUrl in Jsoup !!!!!!!!!!!!!!!!");
        Document document = null;

        try {
            if (Jsoup.connect(url).get() != null) {
//                Jsoup.Connection.Request.validateTLSCertificates(boolean value)
//Jsoup.connect(url).request().validateTLSCertificates(true);
                document = Jsoup.connect(url).get();
                Log.d("123", "document : ==========" + document);
            } else {
                Log.d("123", "IF ELSE JSOUP CNNOT CONNECT TO URL !!");
            }

        } catch (IOException e) {
            Log.d("123", "DOCUMENT-JSOUP canot connect to URL");
            e.printStackTrace();
        }

        //  **  INDEX TABLE  **   ( examples »  LineInTable OK / No answer / Non Active / Notification  /Alert / Non Rec. Msg
        Log.d("123", "ENTERING INDEX TABLE");

        try {
            assert document != null;
            Element indexTable = document.select("table").get(0); // get index table
            Elements indexTds = indexTable.getElementsByTag("Td"); // array of tds in index table
//            HashMap<String, String> indexTableHashMap;
//            indexTableHashMap = new HashMap<>();
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
        Log.d("123", "84 MyJsoup-" + getPrinterTable(document));
        return getPrinterTable(document);
    }




    // **  PRINTERS TABLE **
    private Table getPrinterTable(Document document) {// save all second table(printer table)
        Log.d("123", "ENTERING PRINTERS TABLE");
        Element printersTable = null;
        printersTable = document.select("table").get(1);
        Log.d("123", "94 MyJsoup - printersTable" + printersTable);
        Elements trs = null;// save all line in linesArrInPrintersTable
        trs = printersTable.getElementsByTag("tr"); // array of tr in printers table !

// SUBJECT TR
        Elements tdsID = trs.get(0).getElementsByTag("td");
        for (int i = 0; i < tdsID.size(); i++) {
            Subject subject = new Subject();
            subject.name = tdsID.get(i).text().trim();
            subject.getPosition = i;
            subjects.add(subject);
            Log.d("123", "element tdID id " + tdsID.get(i).text().trim());
        }

        for (Element tr : trs) { // ITER ON TR printer table ***
            String bgColor = null;// hex color

            // save bgColor of the first td in all trs
            try {
                if (!(tr.hasAttr("td") || !(tr.hasAttr("bgcolor")))) {
                    break;
                }
                    bgColor = tr.getElementsByTag("td").get(0).attr("bgcolor").trim();
                    Log.d("123", tr.getElementsByTag("td").toString());

            } catch (Exception e) {
                Log.e("123", "127-MyJsoup - " + e.getMessage());
                e.printStackTrace();
            }
            LineInTable lineInTable = null;
            for (StatusTable statusTable : statusTableArr) {// ITER ON STATUS HEX COLOR ***
                if (statusTable.hexColor.equals(bgColor)) { //if current status equal current printerTable bgcolor
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


                    Elements lineTDS = tr.getElementsByTag("td"); // ARRAY OF TD IN CURRENT TR
                    for (Element td : lineTDS) { // ITER ON TD'S OF CURRENT ROW
                        lineInTable.status = statusTable.statusID;
                        lineInTable.hexColor = statusTable.hexColor;
                        lineInTable.stringOfAllTheLine.add(td.text().trim()); // add single td to strings in LineInTable //need ???
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


//                UrlUtils.addLog(context, e, e.toString());


        return table;
    }
}

