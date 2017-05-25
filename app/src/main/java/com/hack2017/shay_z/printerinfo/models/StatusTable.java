package com.hack2017.shay_z.printerinfo.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shay_z on 03-May-17.
 */

public class StatusTable implements Serializable {
    public String statusID;
    public String hexColor;
    public int count;
    public ArrayList<LineInTable> allLinesOfStatus = new ArrayList<>();


}
