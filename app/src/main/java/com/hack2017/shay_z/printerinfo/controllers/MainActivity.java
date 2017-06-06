package com.hack2017.shay_z.printerinfo.controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.DropBoxDataBase;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentUniversityList.OnUniversitySelectedListener,
        FragmentUniversityPage.OnRefreshSubjectListener, ExeptionInterface {


    private static final String SAVE_UNIVERSITIES = "123";
    FragmentManager fm = getSupportFragmentManager();
    private ArrayList<University> universities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Log.d("shay", "shay on create !");


        DropBoxDataBase list = new DropBoxDataBase("https://dl.1dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0", this);

//        //load preferences
//        SharedPreferences appSharedPrefs = PreferenceManager
//                .getDefaultSharedPreferences(this.getApplicationContext());
//        Gson gson = new Gson();
//        String json = appSharedPrefs.getString(SAVE_UNIVERSITIES, "");
//        Type type = new TypeToken<List<University>>(){}.getType();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        this.universities = universities;
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("CONNECTING ...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        int sizeTemp = universities.size();
        Toast.makeText(this, "" + sizeTemp + "" + sizeTemp + "" + sizeTemp, Toast.LENGTH_LONG).show();

        Log.i("a", "finish universities size= " + universities.size());

// save preferences
//        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor prefEditor = editor.edit();
        Gson gson = new Gson();
//        Log.d("a", "json is "+json);
//        prefEditor.putString(SAVE_UNIVERSITIES, json);
//        prefEditor.commit();



    }


    @Override
    public void onOniversitySelected(int position) {

        Toast.makeText(this, "UNIVERSITY POSITION  " + position, Toast.LENGTH_SHORT).show();
//        fm.beginTransaction().replace(R.id.content_main, PrintersInfoFragment.newInstance(universities.get(position))).commit();

        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(universities.get(position))).commit();
    }


    @Override
    public void refreshSubject(University university) {
        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(university)).commit();
    }


    @Override
    public void onExceptionCallBack(String message) {

    }
}
