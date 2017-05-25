package com.hack2017.shay_z.printerinfo.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shay_z on 10-May-17.
 */

public class UrlUtils {
    public static String getStringFromUrl(String str) {
        URL url = null;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        BufferedReader reader;
        String finalJson = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            finalJson = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalJson;
    }
}
