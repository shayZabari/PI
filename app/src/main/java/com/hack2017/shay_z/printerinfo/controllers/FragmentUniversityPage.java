package com.hack2017.shay_z.printerinfo.controllers;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hack2017.shay_z.printerinfo.Adapters.AdapterBottomSheetRecycler;
import com.hack2017.shay_z.printerinfo.Adapters.AdapterViewPagerPrintersList;
import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.Subject;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */

// FRAGMENT OF UNIVERSITY TABS, VIEW PAGER AND BOTTOM SHEET
public class FragmentUniversityPage extends Fragment {

    private static final String ARG_UNIVERSITY = "arg";
    University university;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterViewPagerPrintersList adapterViewPagerPrintersList;
    OnRefreshSubjectListener onRefreshSubjectListener;
    FloatingActionButton fab;
    private BottomSheetBehavior mBottomSheetBehavior1;


    //default constructor
    public FragmentUniversityPage() {
        // Required empty public constructor
    }

    //create MAIN FRAGMENT (FragmentUniversityPage FRAGMENT )!
    public static Fragment newInstance(University university) {
        FragmentUniversityPage fragmentUniversityPage = new FragmentUniversityPage();
        Bundle args = new Bundle();
        args.putSerializable(ARG_UNIVERSITY, university);
        fragmentUniversityPage.setArguments(args);
        return fragmentUniversityPage;
    }

    // MAIN university_page_fragment XML !!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.university_page_fragment, container, false);

        adapterViewPagerPrintersList = new AdapterViewPagerPrintersList(getContext(), getFragmentManager(), university);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapterViewPagerPrintersList);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v = new View(getContext());
                v.setBackgroundColor(Color.parseColor("#7986CB"));
                v.
                        tab.setCustomView(v);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);

        // working on bottom sheet
        View bottomSheet = v.findViewById(R.id.bottom_sheet1);
        final ArrayList<Subject> subjects = university.table.subjects;
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       if (mBottomSheetBehavior1.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                                           RecyclerView rvSubjects = (RecyclerView) v.findViewById(R.id.rvBottomSheet);
                                           rvSubjects.setAdapter(new AdapterBottomSheetRecycler(getContext(), subjects));
                                           rvSubjects.setLayoutManager(new LinearLayoutManager(getContext()));

                                           mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
                                       } else {
                                           try {
                                               UrlUtils.spSaveCheckboxs(getContext(), subjects, university);
                                           } catch (Exception e) {
                                               Log.e(TAG, "onClick: 96", e);
                                               e.printStackTrace();
                                           }
                                           university.table.subjects = subjects;
                                           mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                           onRefreshSubjectListener.refreshSubject(university);
                                       }
                                   }
                               }
        );
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            university = (University) getArguments().getSerializable(ARG_UNIVERSITY);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRefreshSubjectListener) {
            onRefreshSubjectListener = (OnRefreshSubjectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onRefreshSubjectListener = null;
    }

    interface OnRefreshSubjectListener {
        void refreshSubject(University university);
    }
}

