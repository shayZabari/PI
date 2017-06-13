package com.hack2017.shay_z.printerinfo.controllers;


import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUniversityList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUniversityList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_TAG = "1234";
    // TODO: Rename and change types of parameters
    ArrayList<University>universities;
    OnUniversitySelectedListener onUniversitySelectedListener;


    public FragmentUniversityList() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentUniversityList newInstance(ArrayList<University> universities) {
        FragmentUniversityList fragment = new FragmentUniversityList();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAG,universities);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                universities = (ArrayList<University>) getArguments().getSerializable(ARG_TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_university_list, container, false);
        ListView listView = (ListView) v.findViewById(R.id.lvUniversities);
        listView.setAdapter(new MyList(getActivity(),R.layout.university_row,universities));
        Log.d("123", universities.size()+"");
        return v;
    }

    public interface OnUniversitySelectedListener {
        void onOniversitySelected(int position);
    }

    class MyList extends ArrayAdapter <University>{
        ArrayList<University> list;
        public MyList(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
            onUniversitySelectedListener = (OnUniversitySelectedListener) context;
            list = (ArrayList<University>) objects;

            Log.d("123", "logggg");
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.university_row, parent, false);
                LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.llUniversityRow);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("123", "position= " + position);
                        onUniversitySelectedListener.onOniversitySelected(position);
                    }
                });
                TextView textInfo = (TextView) v.findViewById(R.id.tvInfo1);
                ImageView imageView= (ImageView) v.findViewById(R.id.imgUniversityLogo);
//                imageView.setImageBitmap(list.get(position).getBitmap());
                textInfo.setText(list.get(position).getName());

            }
            return v;
//  return super.getView(position, convertView, parent);


        }
    }
}
