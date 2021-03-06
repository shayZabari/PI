package com.hack2017.shay_z.printerinfo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.Subject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by shay_z on 24-May-17.
 */

public class AdapterPrintersListRecycler extends RecyclerView.Adapter<AdapterPrintersListRecycler.MyViewHolder> {


    private final LayoutInflater inflater;
    StatusTable statusTable ;
    ArrayList<Subject> subjects;
    Context context;

    public AdapterPrintersListRecycler(Context context, StatusTable statusTable, ArrayList<Subject> subjects) {
        inflater = LayoutInflater.from(context);
        this.statusTable = statusTable;
        this.context = context;
        this.subjects = subjects;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_printers_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArrayList<String> currntLine = statusTable.allLinesOfStatus.get(position).stringOfAllTheLine;
        String temp = "";
        for (int i = 0; i < currntLine.size(); i++) {
            try {
                if ((subjects.get(i).checkBoxStatus == true) && currntLine.get(i) != "")
                    temp = temp + ("[ " + currntLine.get(i) + " ]  ");
            } catch (Exception e) {
                Toast.makeText(context, e.toString() + " " + Thread.currentThread().getStackTrace()[2].getLineNumber(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "onBindViewHolder: 57", e);
                e.printStackTrace();
            }

        }
        holder.textView.setText(temp);
//        holder.textView.setText("shay");
    }

    @Override
    public int getItemCount() {
        return statusTable.allLinesOfStatus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_row_recycleview);
        }
    }
}
