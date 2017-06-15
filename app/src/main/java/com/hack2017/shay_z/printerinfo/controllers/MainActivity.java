package com.hack2017.shay_z.printerinfo.controllers;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.DropBoxDataBase;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.MyJsoup;
import com.hack2017.shay_z.printerinfo.models.Table;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentUniversityList.OnUniversitySelectedListener,
        FragmentUniversityPage.OnRefreshSubjectListener, ExeptionInterface {
    DropBoxDataBase d;


    private static final String SAVE_UNIVERSITIES = "123";
    FragmentManager fm = getSupportFragmentManager();
    private ArrayList<University> universities;
    private String dropboxUrl = "https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0";
    private String exceptionMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(getApplicationContext(), "198 ", Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d("shay", "shay on create !");
//refresh();

        //load preferences
        if (UrlUtils.loadUniversities(this) != null) {
            universities = UrlUtils.loadUniversities(this);
        }
//        SharedPreferences appSharedPrefs = PreferenceManager
//                .getDefaultSharedPreferences(this.getApplicationContext());
//        Gson gson = new Gson();
//        String json = appSharedPrefs.getString(SAVE_UNIVERSITIES, "");
//        Type type = new TypeToken<List<University>>() {
//        }.getType();
//        universities = gson.fromJson(json, type);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                if (exceptionMessage != null)
                    Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_LONG).show();
                return true;
            case R.id.refresh:
                refresh();
        }


        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        d = new DropBoxDataBase(dropboxUrl, this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_select_universities) {

            fm.beginTransaction().replace(R.id.content_main, FragmentUniversityList.newInstance(universities)).commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
//            Toast.makeText(this,UrlUtils.getLog(this), Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Title");
            alertDialog.setMessage(UrlUtils.getLog(this));
//            alertDialog.setButton("OK", null);
            AlertDialog alert = alertDialog.create();
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getUniversityDataBase(ArrayList<University> universities) {

        Log.d("123", "Mainactivity_140");
        this.universities = universities;
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("CONNECTING ...");
//        progressDialog.setMessage("Please Wait");
//        progressDialog.setCancelable(false);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        int sizeTemp = universities.size();
        Toast.makeText(this, "" + sizeTemp + "" + sizeTemp + "" + sizeTemp, Toast.LENGTH_LONG).show();

        Log.i("a", "finish universities size= " + universities.size());
        UrlUtils.savePreferences(this, universities);
    }


    @Override
    public void onOniversitySelected(int position) {
        University mUniversity = universities.get(position);
        new MyJsoup(this).execute(mUniversity);
//        Toast.makeText(this, "UNIVERSITY POSITION  " + position, Toast.LENGTH_SHORT).show();
//        fm.beginTransaction().replace(R.id.content_main, PrintersInfoFragment.newInstance(universities.get(position))).commit();


    }


    @Override
    public void refreshSubject(University university) {
        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(university)).commit();
    }


    @Override
    public void onExceptionCallBack(String message) {
        exceptionMessage = message;
        Log.e("123", "123");
        d.cancel(true);
        Log.e("123", message);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
//        maketoast();
    }

    private void maketoast() {
        Log.d("123", "this = " + this.toString());
//        Toast.makeText(this,"toasting",Toast.LENGTH_LONG).show();
    }


    public void onTableFinished(University university) {
        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(university)).commit();
    }
}
