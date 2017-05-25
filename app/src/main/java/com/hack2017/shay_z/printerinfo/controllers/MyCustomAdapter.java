package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * Created by shay_z on 22-Apr-17.
 */

public class MyCustomAdapter extends BaseAdapter {
    private ArrayList<University> universities;
    //    Context activity;
//    PrinterInfo pi;
    private static LayoutInflater inflater = null;
    private MainActivity activity;
    private View view;


    public MyCustomAdapter(ArrayList<University> universities, MainActivity activity) {
        this.universities = universities;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return universities.size();
    }

    @Override
    public Object getItem(int position) {
        return universities.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;

            ArrayList<StatusTable> statusTableArr = universities.get(position).table.statusTableArr;
//        Item items = data.get(pos);
        ViewHolder viewHolder;
        if (convertView == null) {

            view = inflater.inflate(R.layout.row,parent,false );
            LinearLayout linear = (LinearLayout) view.findViewById(R.id.linear);
            viewHolder = new ViewHolder();
//            viewHolder.color= statusTableArr.get(0).hexColor;
//            viewHolder.status= statusTableArr.get(0).statusID;
//            viewHolder.counter= statusTableArr.get(0).count;
//            String color2= statusTableArr.get(1).hexColor;
//            String status2= statusTableArr.get(1).statusID;
//            int counter2= statusTableArr.get(1).count;

            for (StatusTable statusTable : statusTableArr) {
                TextView tvTemp = new TextView(activity);
                tvTemp.setText(" "+statusTable.statusID+" [ " +statusTable.count+" ] ");
                tvTemp.setBackgroundColor(Color.parseColor(statusTable.hexColor));
                linear.addView(tvTemp);

            }



//            viewHolder.status = pi.lines.get(pos).status;
//            viewHolder.counter = pi.lines.get(pos).count;
//            data.get(pos).setName( (TextView) view.findViewById(R.id.tvName));
//            ((TextView) view.findViewById(R.id.tvStatus)).setText(viewHolder.status);
//            ((TextView) view.findViewById(R.id.tvCounter)).setText(Integer.toString(viewHolder.counter));
//            ((TextView) view.findViewById(R.id.tvStatus1)).setBackgroundColor(Color.parseColor(viewHolder.color));
//            ((TextView) view.findViewById(R.id.tvStatus1)).setText(viewHolder.status+" ["+viewHolder.counter+"]");
            ((ImageView)(view.findViewById(R.id.imgLogo))).setImageDrawable(universities.get(position).getLogo());
//            ((TextView) view.findViewById(R.id.tvStatus2)).setBackgroundColor(Color.parseColor(color2));
//            ((TextView) view.findViewById(R.id.tvStatus2)).setText(status2+" ["+counter2+"]");

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
//            viewHolder.name.setText(items.getName());
        }
        return view;
    }

    private class ViewHolder {
        String status, color;
        int counter;

    }
}
