package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.LineInTable;
import com.hack2017.shay_z.printerinfo.models.StatusTable;
import com.hack2017.shay_z.printerinfo.models.University;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TheList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TheList extends Fragment {
    private static final String Arg = "arg";
    University university;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager mPager;
    MyPagerAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;

    public TheList() {
        // Required empty public constructor
    }

// MAIN fragment_list XML !!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
//        toolbar = (Toolbar) v.findViewById(R.id.app_bar);
        mPager = (ViewPager) v.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        mAdapter = new MyPagerAdapter(getFragmentManager(), university);
        mPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mPager);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            university = (University) getArguments().getSerializable(Arg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //create MAIN FRAGMENT (TheList FRAGMENT )!
    public static Fragment newInstance(University university) {
        TheList theList = new TheList();
        Bundle args = new Bundle();
        args.putSerializable(Arg, university);
        theList.setArguments(args);
        return theList;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter {
    University university;

    // constructor of MyPagerAdapter
    public MyPagerAdapter(FragmentManager fm, University university) {
        super(fm);
        this.university = university;
    }

    // create new MyFragment in the current position and return it to the adapter
    @Override
    public Fragment getItem(int position) {
        MyFragment myFragment = MyFragment.newInstance(university.table.statusTableArr.get(position));
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
        return university.table.statusTableArr.get(position).statusID+" "+university.table.statusTableArr.get(position).allLinesOfStatus.size();
    }

    // CREATING TAG FRAGMENT
    public static class MyFragment extends Fragment {
        private static final String ARG_LINE_IN_TABLE = "status_table";

        // create instance of MyFragment and put "lineInTable" to the bundle
        public static MyFragment newInstance(StatusTable lineInTable) {
            MyFragment fragment = new MyFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_LINE_IN_TABLE, lineInTable);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            ListView listView;
            Bundle arguments = getArguments();
            StatusTable statusTable = (StatusTable) arguments.getSerializable(ARG_LINE_IN_TABLE);
            View v = inflater.inflate(R.layout.temp_in_pageradapter, container, false);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv);
            recyclerView.setAdapter(new RecyclerAdapter(getContext(),statusTable));
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
