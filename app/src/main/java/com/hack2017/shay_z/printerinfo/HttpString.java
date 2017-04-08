package com.hack2017.shay_z.printerinfo;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shay_z on 07-Apr-17.
 */

public class HttpString extends AsyncTask<URL, String, ArrayList<PrinterLine>> {
    StringBuffer buffer;
    ArrayList<PrinterLine> printerLines = new ArrayList<>();


    @Override
    protected ArrayList<PrinterLine> doInBackground(URL... urls) {
        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) urls[0].openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String importedString = buffer.toString();


            Document document = Jsoup.parse(importedString);

            // TABLE1
            Element table1 = document.select("Table").get(0);
            Elements tds = table1.getElementsByTag("Td");

            HashMap<String, String> tdsMap = new HashMap<>();
            for (Element td : tds) {
                tdsMap.put(td.attr("bgcolor"), td.text().replace("\u00a0", ""));

            }

            // TABLE2
            Element table2 = document.select("Table").get(1);
            Elements tb2TRs = table2.getElementsByTag("tr");

            for (String td : tdsMap.keySet()) {
//                        Log.d("ER", td + " " + tdsMap.get(td).trim());


                for (Element tb2TR : tb2TRs) {
                    Element tdd = tb2TR.select("td").get(0); // get first td from tr
                    if (td.equals(tdd.attr("bgcolor"))) {
                        String value1 = tdsMap.get(td);
                        String key1 = td;
                        printerLines.add(new PrinterLine(key1, value1));
//                            Log.d("asdf", tdd.attr("bgcolor"));
//                            Log.d("table2:", "colors: " + tdd.toString());
                    }

                }


            }
            return printerLines;

//                Elements t1Trs = table1.select("tr");
//                Elements t1Td = table1.select("td");
//                Elements greenElementsInTable1 = t1Td.attr("bgcolor", "#00BB00");
//                for (Element element : greenElementsInTable1) {
//                    String val = element.val();
//                }
//                Element t1Td = t1Trs.select("td").get(0);
//                String attribu = t1Td.attr("bgcolor");
//                String value = t1Td.text();


//                for (Element td : t1Td) {
//
//                }


//                Log.d("shayLog", "table1 :"+table1);
//                Log.d("shayLog", "t1trs:"+attribu);

//                for (Element element : elements) {
//                    Element table = element.getElementsByTag("Table").get(0);
//                    Elements rows = table.getElementsByTag("TR");
//                }


//                String[] table = importedString.split("<table");
//                for (int i = 1; i < table.length; i++) {
//                    Log.d("shayshay", i+" number");
////                    if (splitting[i].contains("<td>")) {
////
////                    }
//                }

//                for (String s : buffer.toString().split("</tr>")) {
//                    Log.d("shay", s);
//                }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PrinterLine> printerLines) {
        super.onPostExecute(printerLines);
    }
}
