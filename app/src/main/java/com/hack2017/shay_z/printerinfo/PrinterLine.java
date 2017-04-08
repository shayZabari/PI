package com.hack2017.shay_z.printerinfo;

/**
 * Created by shay_z on 06-Apr-17.
 */

public class PrinterLine {
    String status;
    String color;

    @Override
    public String toString() {
        return "PrinterLine{" +
                "status='" + status + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public PrinterLine(String status, String color) {
        this.status = status;
        this.color = color;
    }
}
