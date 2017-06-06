package com.hack2017.shay_z.printerinfo.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.controllers.MainActivity;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.Subject;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.logging.ErrorManager;

/**
 * Created by shay_z on 30-May-17.
 */

public class AdapterViewPagerPrintersList extends FragmentStatePagerAdapter {
    private final Context context;
    University university;

    // constructor of AdapterViewPagerPrintersList
    public AdapterViewPagerPrintersList(Context context, FragmentManager fm, University university) {
        super(fm);
        this.university = university;
        this.context = context;
    }

    /**
     * UNIVERSITY
     * LOGO
     * NAME
     * TABLE\
     * lineInTableArrayList
     * statusTableArr\
     * 0\
     * allLinesOfStatus
     * hexColor
     * statusID
     * count
     * 1\
     * *          allLinesOfStatus
     * hexColor
     * statusID
     * count
     */


    // create new MyFragment in the current position and return it to the adapter
    @Override
    public Fragment getItem(int position) {
        MyFragment myFragment = MyFragment.newInstance(university.table.statusTableArr.get(position), university);
        return myFragment;
    }

    // return the count of the
    @Override
    public int getCount() {
        return university.table.statusTableArr.size();
    }

    // add name to the tag
    @Override
    public CharSequence getPageTitle(int position) {
        return university.table.statusTableArr.get(position).statusID + " " + university.table.statusTableArr.get(position).allLinesOfStatus.size();
    }

    interface OnNotifyDataSetCHangeListener {
        boolean onNotifyDataSetChange = false;
    }

    // CREATING TAG FRAGMENT
    public static class MyFragment extends Fragment {
        private static final String ARG_LINE_IN_TABLE = "status_table";
        private static final String ARG_SUBJECTS = "checkBox_status";

        // create instance of MyFragment and put "lineInTable" to the bundle
        public static MyFragment newInstance(StatusTable lineInTable, University university) {
            MyFragment fragment = new MyFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_LINE_IN_TABLE, lineInTable);
            args.putSerializable(ARG_SUBJECTS, university.table.subjects);
            fragment.setArguments(args);
            return fragment;
        }

        //create bottomsheet
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            ListView listView;
            Bundle arguments = getArguments();
            StatusTable statusTable = (StatusTable) arguments.getSerializable(ARG_LINE_IN_TABLE);
            ArrayList<Subject> subjects = (ArrayList<Subject>) arguments.getSerializable(ARG_SUBJECTS);
            View v = inflater.inflate(R.layout.printers_list_recycler, container, false);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv_printers_lines);
            recyclerView.setAdapter(new AdapterPrintersListRecycler(getContext(), statusTable, subjects));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            listView = (ListView) v.findViewById(R.id.lv_fragment_list);
//            listView.setAdapter(new LVAdapter(arguments));
//            return v;
//            ArrayList<LineInTable> a = statusTable.allLinesOfStatus;
//            ArrayList<String> rows = a.toString();
//            ListView lv = (ListView) v.findViewById(R.id.lv);
//            ArrayAdapter<LineInTable> adapter = new ArrayAdapter<LineInTable>(getContext(), android.R.layout.simple_list_item_1, a);
//            lv.setAdapter(adapter);
//            TextView myText = new TextView(getActivity());
//            if (statusTable.allLinesOfStatus.size()>0) {
//
//            String temp = statusTable.allLinesOfStatus.get(0).stringOfAllTheLine.get(5);
////            myText.setText(temp);
//            }else {

//            myText.setText("error");
//            }
//            myText.setGravity(Gravity.CENTER);
            return v;
        }
    }
}
