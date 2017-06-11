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
    Table table = new Table();
    ArrayList<StatusTable> statusTableArr = new ArrayList<>();
    ArrayList<Subject> subjects = new ArrayList<>();
    private String url;


    public Document getDocument(String url) {

        try {
            if (Jsoup.connect(url).get() != null)
                Document temp = Jsoup.connect(url) // TODO: 09-Jun-17   
                return Jsoup.connect(url).get();
            ;
        } catch (IOException e) {
            e.printStackTrace();
        } 
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

    public Table getTable(String url) {
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
                }
                Elements lineTDS = tr.getElementsByTag("td"); // ARRAY OF TD IN CURRENT TR
                for (Element td : lineTDS) { // ITER ON TD'S OF CURRENT ROW
                    lineInTable.status = statusTable.statusID;
                    lineInTable.hexColor = statusTable.hexColor;
                    lineInTable.stringOfAllTheLine.add(td.text().trim()); // add single td to strings in LineInTable //need ???
                }
                statusTable.allLinesOfStatus.add(lineInTable);
            }
            
        }
        

        
//        return getPrinterTable(document);
        return table;
    }

    private Elements getTrsFromPrintersTable(Document document) {
        Log.d("123", "ENTERING PRINTERS TABLE");
        Element printersTable = null;
        printersTable = document.select("table").get(1);
        Log.d("123", "94 MyJsoup - printersTable" + printersTable);
        Elements trs = null;// save all line in linesArrInPrintersTable
        trs= printersTable.getElementsByTag("tr"); // array of tr in printers table !
        Elements trsGood = new Elements();
        for (Element tr : trs) {
            if ((tr.hasAttr("td") && (tr.hasAttr("bgcolor")))) {
                trsGood.add(tr);
            }
        }
        return trsGood;
    }


//    // **  PRINTERS TABLE **
//    private Table getPrinterTable(Document document) {// save all second table(printer table)
//        Log.d("123", "ENTERING PRINTERS TABLE");
//        Element printersTable = null;
//        printersTable = document.select("table").get(1);
//        Log.d("123", "94 MyJsoup - printersTable" + printersTable);
//        Elements trs = null;// save all line in linesArrInPrintersTable
//        trs = printersTable.getElementsByTag("tr"); // array of tr in printers table !
//        

//// SUBJECT TR
//        Elements tdsID = trs.get(0).getElementsByTag("td");
//        for (int i = 0; i < tdsID.size(); i++) {
//            Subject subject = new Subject();
//            subject.name = tdsID.get(i).text().trim();
//            subject.getPosition = i;
//            subjects.add(subject);
//            Log.d("123", "element tdID id " + tdsID.get(i).text().trim());
//        }

//        for (Element tr : trs) { // ITER ON TR printer table ***
//            String bgColor = null;// hex color
//
//            // save bgColor of the first td in all trs
//            try {
//                if (!(tr.hasAttr("td") || !(tr.hasAttr("bgcolor")))) {
//                    break;
//                }
//                bgColor = tr.getElementsByTag("td").get(0).attr("bgcolor").trim();
////                Log.d("123", tr.getElementsByTag("td").toString());
//
//            } catch (Exception e) {
//                Log.e("123", "127-MyJsoup - " + e.getMessage());
//                e.printStackTrace();
//            }
//            LineInTable lineInTable = null;
//            for (StatusTable statusTable : statusTableArr) {// ITER ON STATUS HEX COLOR ***
//                if (statusTable.hexColor.equals(bgColor)) { //if current status equal current printerTable bgcolor
//                    statusTable.count += 1;
//                  lineInTable = new LineInTable();  // NEW OBJECT OF LineInTable
//                    Elements lineTDS = tr.getElementsByTag("td"); // ARRAY OF TD IN CURRENT TR
//                    for (Element td : lineTDS) { // ITER ON TD'S OF CURRENT ROW
//                        lineInTable.status = statusTable.statusID;
//                        lineInTable.hexColor = statusTable.hexColor;
//                        lineInTable.stringOfAllTheLine.add(td.text().trim()); // add single td to strings in LineInTable //need ???
//                    }
//
////                        lines.add(lineInTable); temp removed 0524 17:19
//                    statusTable.allLinesOfStatus.add(lineInTable); // TODO: 24-May-17
//                }
//                table.statusTableArr = statusTableArr;
//                table.subjects = subjects;
//            }
//        }
//
//        // END OF ALL ITERS ***
//
////        table.lineInTableArrayList = lines; temp removed // TODO: 02-Jun-17
//
//
////                UrlUtils.addLog(context, e, e.toString());
//
//
//        return table;
//    }

    public MyJsoup(Context context) {
        this.context = context;
    }

}

