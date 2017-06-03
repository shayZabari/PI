package com.hack2017.shay_z.printerinfo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.Subject;

import java.util.ArrayList;

/**
 * Created by shay_z on 29-May-17.
 */

public class AdapterBottomSheetRecycler extends RecyclerView.Adapter<AdapterBottomSheetRecycler.SubjectHolder> {
    Context context;
    ArrayList<Subject> subjects;
    LayoutInflater inflater;

    public AdapterBottomSheetRecycler(Context context, ArrayList<Subject> subjects) {
        inflater = LayoutInflater.from(context);

        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_subject, parent, false);
        SubjectHolder holder = new SubjectHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final SubjectHolder subjectHolder, final int position) {
        subjectHolder.textView.setText(subjects.get(position).name);
        subjectHolder.checkBox.setChecked(subjects.get(position).checkBoxStatus);
        subjectHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox c = (CheckBox) v;
                subjects.get(position).checkBoxStatus = c.isChecked();

            }
        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class SubjectHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        public SubjectHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvSubjectItem);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbSubject);
        }
    }
}


