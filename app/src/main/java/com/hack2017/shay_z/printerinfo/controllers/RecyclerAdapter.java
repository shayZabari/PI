package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.StatusTable;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by shay_z on 24-May-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    private final LayoutInflater inflater;
    StatusTable statusTable ;

    public RecyclerAdapter(Context context, StatusTable statusTable) {
        inflater = LayoutInflater.from(context);
        this.statusTable = statusTable;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_recycle_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(statusTable.allLinesOfStatus.get(position).stringOfAllTheLine.toString());
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
