package com.hack2017.shay_z.printerinfo.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.DropBoxDataBase;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.MyJsoup;
import com.hack2017.shay_z.printerinfo.models.Subject;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentUniversityList.OnUniversitySelectedListener,
        FragmentUniversityPage.OnRefreshSubjectListener, ExeptionInterface {
    private static final String SAVE_UNIVERSITIES = "123";
    private static final String TAG = "123";
    public static ArrayList<University> universities1;
    DropBoxDataBase d;
    FragmentManager fm = getSupportFragmentManager();
    int universityPosition;
    Intent intent;
    private String dropboxUrl = "https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0";
    private String exceptionMessage;
    //    AlertDialog alert;
    private ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();
        refreshUniversities();
    }

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
//        Intent i = new Intent(getBaseContext(), MyService.class);
        Log.d("shay", "intent");
//        getBaseContext().startService(i);
        Log.d("shay", "intent1");
//refreshUniversities();

        //load preferences
        if (UrlUtils.spLoadUniversities(this) != null) {
            universities1 = UrlUtils.spLoadUniversities(this);
        }
        if (UrlUtils.spLoadUniversityPosition(this) > -1) {
            Toast.makeText(this, "position > -1", Toast.LENGTH_SHORT).show();
//            getSupportActionBar().setTitle("test 75 main");
            setToolBar();

            onOniversitySelected(UrlUtils.spLoadUniversityPosition(this));
        }
//        refreshUniversities();
//        SharedPreferences appSharedPrefs = PreferenceManager
//                .getDefaultSharedPreferences(this.getApplicationContext());
//        Gson gson = new Gson();
//        String json = appSharedPrefs.getString(SAVE_UNIVERSITIES, "");
//        Type type = new TypeToken<List<University>>() {
//        }.getType();
//        universities1 = gson.fromJson(json, type);
    }

    private void setToolBar() {
        if (universities1 != null) {

        getSupportActionBar().setTitle(universities1.get(universityPosition).getName());
        getSupportActionBar().setSubtitle(DateFormat.getDateTimeInstance().format(new Date()));
        }
//        ImageView imageView = new ImageView(this);
//        if (universities1.get(universityPosition).getLogoUrl() != null) {
//            Picasso.with(this).load(universities1.get(universityPosition).getLogoUrl()).into(imageView);
////            Drawable drawable = imageView.getDrawable();
////            drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
////                    (int)(drawable.getIntrinsicHeight()*0.5));
//            getSupportActionBar().setLogo(imageView.getDrawable());
//
//        } else {
//            Toast.makeText(this, "logourl null", Toast.LENGTH_SHORT).show();
//        }
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
                onOniversitySelected(universityPosition);
        }


        return super.onOptionsItemSelected(item);
    }

    private void refreshUniversities() {
        addProgressDialog();
//        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        d = new DropBoxDataBase(dropboxUrl, this);
        setToolBar();
    }

    private void addProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating database..");
        progressDialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_select_universities) {
            if (UrlUtils.spLoadUniversities(this) != null) {
                universities1 = UrlUtils.spLoadUniversities(this);
            } else {
                refreshUniversities();
            }
            fm.beginTransaction().replace(R.id.content_main, FragmentUniversityList.newInstance(universities1)).commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            intent = new Intent(this, MyService.class);
            Log.d(TAG, "onNavigationItemSelected: start service 191");
            startService(intent);
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "onNavigationItemSelected: stopping service 192");
            stopService(intent);
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

    // callback from DropBoxDataBase
    public void getDropBoxDatbase(ArrayList<University> universities) throws Exception {
        if (universities1 != null) {

        }
        Log.d("123", "Mainactivity_155");
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("CONNECTING ...");
//        progressDialog.setMessage("Please Wait");
//        progressDialog.setCancelable(false);
        progressDialog.cancel();
//        findViewById(R.id.progressBar).setVisibility(View.GONE);
        int sizeTemp = universities.size();
        Toast.makeText(this, "FOUND " + sizeTemp + " UNIVERSITIES", Toast.LENGTH_LONG).show();
        Log.i("a", "finish universities1 size= " + universities.size());
        for (University university : universities) {
//        Picasso.with(this).load(university.getLogoUrl())
            ArrayList<Subject> tempSubject = UrlUtils.spLoadCheckboxes(this, university);
            if (tempSubject != null) {

            for (int i = 0; i < university.table.subjects.size(); i++) {
                university.table.subjects.get(i).checkBoxStatus = tempSubject.get(i).checkBoxStatus;
                university.table.subjects.get(i).getPosition = tempSubject.get(i).getPosition;
            }
            }
            universities1 = universities;
        }
        UrlUtils.spSaveUniversities(this, universities);
    }

    // calback from FragmentUniversityList
    @Override
    public void onOniversitySelected(int position) {

        universityPosition = position;
        UrlUtils.spSaveUniversityPosition(this, position);
        University selectedUniversity = new University();
        selectedUniversity = universities1.get(position);
        Log.d("123", "");
        setToolBar();
        Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()), Toast.LENGTH_SHORT).show();
        new MyJsoup(this).execute(selectedUniversity);
//        Toast.makeText(this, "UNIVERSITY POSITION  " + position, Toast.LENGTH_SHORT).show();
//        fm.beginTransaction().replace(R.id.content_main, PrintersInfoFragment.newInstance(universities1.get(position))).commit();


    }

    // callback after fab collapsed(FragmentUniversityPage)
    @Override
    public void refreshSubject(University university) {
        universities1.set(universityPosition,university);
        UrlUtils.spSaveUniversities(this, universities1);
        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(university)).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void onExceptionCallBack(String message) {
        exceptionMessage = message;
        Log.e("123", "123");
        d.cancel(true);
        Log.e("123", message);
//        findViewById(R.id.progressBar).setVisibility(View.GONE);
//        maketoast();
    }

    private void maketoast() {
        Log.d("123", "this = " + this.toString());
//        Toast.makeText(this,"toasting",Toast.LENGTH_LONG).show();
    }

    // callback after jsoup finished
    public void onTableFinished(University university) {
//        Log.d("123", "test "+universities1.get(0).table.subjects.get(0).checkBoxStatus+"-");
//        for (int i = 0; i < university.table.subjects.size(); i++) {
//                    university.table.subjects.get(i).checkBoxStatus=
//            universities1.get(universityPosition).table.subjects.get(i).checkBoxStatus;
//        }
        universities1.set(universityPosition, university);
        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(university)).commit();
    }
}
