package com.hack2017.shay_z.printerinfo.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shay_z on 21-Apr-17.
 */

public  class LineInTable implements Serializable {
    public String status = null;
    public String hexColor = null;
    public ArrayList<String> stringOfAllTheLine = new ArrayList<>();

    @Override
    public String toString() {
        return "LineInTable{" +
                "stringOfAllTheLine=" + stringOfAllTheLine +
                '}';
    }
}
