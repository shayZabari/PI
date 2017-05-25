package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPrinterStatusSelectedListener} interface
 * to handle interaction events.
 * Use the {@link PrintersInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrintersInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_UNIVERSITY = "param1";
    private static final String ARG_PARAM2 = "param2";
    University university;
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPrinterStatusSelectedListener mListener;


    public PrintersInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment PrintersInfoFragment.
     * @param university
     */
    // TODO: Rename and change types and number of parameters
    public static PrintersInfoFragment newInstance(University university) {
        PrintersInfoFragment fragment = new PrintersInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_UNIVERSITY,  university);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            university = (University) getArguments().getSerializable(ARG_UNIVERSITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_printers_info, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.gvStatuses);
        gridView.setAdapter(new MyList(getActivity(),R.layout.printer_status_gridview_row,university.table.statusTableArr));
        Log.d("a", "onCreateView inside PrintersInfoFragment");
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPrinterStatusSelected(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPrinterStatusSelectedListener) {
            mListener = (OnPrinterStatusSelectedListener) context;
            this.context = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPrinterStatusSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnPrinterStatusSelectedListener {
        // TODO: Update argument type and name
        void onPrinterStatusSelected(Uri uri);
    }

    class MyList extends ArrayAdapter {
        ArrayList<StatusTable > statuses;

        public MyList(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
//            onUniversitySelectedListener = (UniversityListFragment.OnUniversitySelectedListener) context;
            statuses = (ArrayList<StatusTable>) objects;

            Log.d("a", "logggg");
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Log.d("a", "getView inside PrintersInfoFragment");
            View v = convertView;
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (v == null) {
                v = inflater.inflate(R.layout.printer_status_gridview_row, null); // create new xml .
                TextView tv = (TextView) v.findViewById(R.id.tv_gv_row);
                tv.setText(statuses.get(position).statusID+" [ "+statuses.get(position).count+" ]");
                tv.setBackgroundColor(Color.parseColor(statuses.get(position).hexColor));


//                tv.setText("asdfasd");


            } else {

            }
            return v;
//  return super.getView(position, convertView, parent);


        }
    }

}
