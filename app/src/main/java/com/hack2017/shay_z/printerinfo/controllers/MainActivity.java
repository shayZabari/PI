package com.hack2017.shay_z.printerinfo.controllers;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.hack2017.shay_z.printerinfo.models.DatabaseDropbox;
import com.hack2017.shay_z.printerinfo.R;
import com.hack2017.shay_z.printerinfo.models.DatabaseTable;
import com.hack2017.shay_z.printerinfo.models.ExeptionInterface;
import com.hack2017.shay_z.printerinfo.models.Subject;
import com.hack2017.shay_z.printerinfo.models.University;
import com.hack2017.shay_z.printerinfo.models.UrlUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentUniversityList.OnUniversitySelectedListener,
        FragmentUniversityPage.OnRefreshSubjectListener, ExeptionInterface, DatabaseTable.OnDataBaseTableReceivedListener, TimePickerDialog.OnTimeSetListener {
    private static final String SAVE_UNIVERSITIES = "123";
    private static final String TAG = "123";
    public static ArrayList<University> universities1;
    DatabaseDropbox d;
    FragmentManager fm = getSupportFragmentManager();
    int universityPosition;
    Intent intent;
    private String dropboxUrl = "https://dl.dropboxusercontent.com/s/fjouslzbhn5chlh/printerInfoApp.txt?dl=0";
    private String exceptionMessage;
    //    AlertDialog alert;
    private ProgressDialog progressDialog;
    private int timePickerHour;
    private int timePickerMinute;

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 03-Jul-17
        refreshUniversities();
//        testMyService();

    }

    public void testMyService() {
        // use this to start and trigger a service
        Intent i= new Intent(this, MyService.class);
// potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdeptAndroidJobCreator aajc = new AdeptAndroidJobCreator();
        JobManager.create(this).addJobCreator(new AdeptAndroidJobCreator());


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //load preferences
        if (UrlUtils.spLoadUniversities(this) != null) {//if not null then load from sp
            universities1 = UrlUtils.spLoadUniversities(this);
        }
        if (UrlUtils.spLoadUniversityPosition(this) > -1) { //if > -1 then return position from sp
            Toast.makeText(this, "position > -1", Toast.LENGTH_SHORT).show();
//            getSupportActionBar().setTitle("test 75 main");
            setToolBar();

            onOniversitySelected(UrlUtils.spLoadUniversityPosition(this));
        }
    }

    private void notifications(boolean start) {// TODO: 13/08/2017 delete ?
//        Calendar cal = Calendar.getInstance();
////        Date currentTime = cal.getTime();
//        int selectedHour = 13;
//        // TODO: 20/07/2017 trying set specific hour
//        int from = (int) TimeUnit.HOURS.toSeconds((selectedHour - (cal.get(Calendar.HOUR))));
//        int to = (int) (from + TimeUnit.HOURS.toSeconds(1));
////        currentTime.getTime();

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        addProgressDialog("Please Wait..", "Updating Database");
//        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        d = new DatabaseDropbox(dropboxUrl, this);
        setToolBar();
    }

    private void addProgressDialog(String setTitle, String setMessage) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(setTitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(setMessage);
        progressDialog.show();
    }

    // navigation drawer
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
            if (universities1 != null) {
                fm.beginTransaction().replace(R.id.content_main, FragmentUniversityList.newInstance(universities1)).commit();
            } else {
                maketoast("universities1 is  null");
            }
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.add_notification) {

            new TimePickerDialog(MainActivity.this, this, timePickerHour, timePickerMinute, true).show();
        } else if (id == R.id.nav_share) {
//            notifications(false);
//            Log.d(TAG, "onNavigationItemSelected: stopping service 192");
//            stopService(intent);
        } else if (id == R.id.stop_service) {
            notifications(false);
            Toast.makeText(this, "Service Stopped!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,UrlUtils.getLog(this), Toast.LENGTH_LONG).show();
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//            alertDialog.setTitle("Title");
//            alertDialog.setMessage(UrlUtils.getLog(this));
////            alertDialog.setButton("OK", null);
//            AlertDialog alert = alertDialog.create();
//            alert.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void evernoteJobs(int hourChoosed, int minuteChoosed) {
        EvernoteDailyJobTest.schedule(hourChoosed,minuteChoosed);// testing EvernoteDailyJob
        //        Log.d(TAG, "evernoteJobs: starting evernoteJobs from main activity");
//        new JobRequest.Builder(MyEvernoteJob.JOB_TAG)
//                .setPeriodic(TimeUnit.MINUTES.toMillis(16))
////                .setExecutionWindow(TimeUnit.MINUTES.toMillis(15),TimeUnit.MINUTES.toMillis(16))
//                .setUpdateCurrent(true)
//                .build()
//        .schedule();

        maketoast("test evernote job tempppp");
        //        tempEvernoteMethod(hourChoosed, minuteChoosed);
    }

    private void tempEvernoteMethod(int hourChoosed, int minuteChoosed) {
        Calendar calendar = Calendar.getInstance();
        long currentHour = calendar.get(Calendar.HOUR_OF_DAY);// get current hour
        long currentMinute = calendar.get(Calendar.MINUTE); // get current minute
//        long timeChoosedInMinutes = TimeUnit.HOURS.toMinutes(hourChoosed) + TimeUnit.MINUTES.toMillis(minuteChoosed);

        long startMill = TimeUnit.MINUTES.toMillis((minuteChoosed) + (60 * (hourChoosed + 24 - currentHour)) + (60 - currentMinute));
//        long startMill = TimeUnit.HOURS.toMillis(hourChoosed + 24 - currentHour) + TimeUnit.MINUTES.toMillis(60 - currentMinute);
        long totalMinutesLeft = startMill / 1000 / 60;// sum of minutes to choosed hour.
        long hoursLeft = startMill / 3600000;
        long minutesLeft = totalMinutesLeft - ((startMill / 3600000) * 60);
        String timeLeft = hoursLeft + ":" + minutesLeft;

        long endMill = startMill + TimeUnit.HOURS.toMillis(2);
        Log.d(TAG, "evernoteJobs: " + hoursLeft);
        new JobRequest.Builder(MyEvernoteJob.JOB_TAG)
                .setExecutionWindow(TimeUnit.MINUTES.toMillis(16), endMill)
//                .setPersisted(true) // TODO: 04/09/2017 not working with new 4:26.0.1 support library
                .setUpdateCurrent(true)
                .build()
                .schedule();
        Toast.makeText(this, "Notification set for every " + hourChoosed + ":" + minuteChoosed + " each day.", Toast.LENGTH_SHORT).show();
    }

    // callback from DatabaseDropbox
    public void getDropBoxDatbase(ArrayList<University> universities) {
        progressDialog.cancel();
        if (universities == null) {
            Log.e(TAG, "getDropBoxDatbase: in mainactivity 215 " + exceptionMessage);
            return;
        }
        Log.d("123", "Mainactivity_155");
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("CONNECTING ...");
//        progressDialog.setMessage("Please Wait");
//        progressDialog.setCancelable(false);
//        findViewById(R.id.progressBar).setVisibility(View.GONE);
        int sizeTemp = universities.size();
        maketoast("FOUND " + sizeTemp + " UNIVERSITIES");
        Log.i("a", "finish universities1 size= " + universities.size());
        for (University university : universities) {
            ArrayList<Subject> tempSubject = null;
            try {

                if (UrlUtils.spLoadCheckboxes(this, university) != null && university.table != null) {
                    tempSubject = UrlUtils.spLoadCheckboxes(this, university);
                    for (int i = 0; i < university.table.subjects.size(); i++) {
                        university.table.subjects.get(i).checkBoxStatus = tempSubject.get(i).checkBoxStatus;
                        university.table.subjects.get(i).getPosition = tempSubject.get(i).getPosition;
                    }
                } else {
                    Log.e(TAG, "getDropBoxDatbase: universityFromIntent.table is nullllll 238");
                    maketoast("universityFromIntent table is null");
                }

            } catch (Exception e) {
                maketoast(e.getMessage());
            }
            UrlUtils.spSaveUniversities(this, universities);
            universities1 = universities;
        }

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
        new DatabaseTable(this).execute(selectedUniversity);
//        Toast.makeText(this, "UNIVERSITY POSITION  " + position, Toast.LENGTH_SHORT).show();
//        fm.beginTransaction().replace(R.id.content_main, PrintersInfoFragment.newInstance(universities1.get(position))).commit();


    }

    // callback after fab collapsed(FragmentUniversityPage)
    @Override
    public void refreshSubject(University university) {
        universities1.set(universityPosition, university);
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

        Log.e(TAG, "mainActivity-onExceptionCallBack: ");
        maketoast(message);
        d.cancel(true);


//        findViewById(R.id.progressBar).setVisibility(View.GONE);
//        maketoast();
    }

    private void maketoast(String message) {
        Log.d("123", "294 = " + message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // callback after jsoup finished
    public void onTableFinished(University university) {
//        Log.d("123", "test "+universities1.get(0).table.subjects.get(0).checkBoxStatus+"-");
//        for (int i = 0; i < universityFromIntent.table.subjects.size(); i++) {
//                    universityFromIntent.table.subjects.get(i).checkBoxStatus=
//            universities1.get(universityPosition).table.subjects.get(i).checkBoxStatus;
//        }

    }

    @Override
    public void onDataBaseTableReceived(University university) {
        universities1.set(universityPosition, university);
        fm.beginTransaction().replace(R.id.content_main, FragmentUniversityPage.newInstance(university)).commit();
    }

    @Override // callback when timepicker ui done.
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        evernoteJobs(hour, minute);
    }
}
