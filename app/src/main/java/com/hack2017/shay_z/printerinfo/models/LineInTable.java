package com.hack2017.shay_z.printerinfo.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shay_z on 21-Apr-17.
 */

public  class LineInTable implements Serializable {
    public String status = null;
    public String hexColor = null;
    //    public int count=0; temp removed // TODO: 02-Jun-17  
    public ArrayList<String> stringOfAllTheLine = new ArrayList<>();

    @Override
    public String toString() {
        return "LineInTable{" +
                "stringOfAllTheLine=" + stringOfAllTheLine +
                '}';
    }
}
