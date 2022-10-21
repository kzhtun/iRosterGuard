package com.info121.iguard.activities;

import static com.info121.iguard.App.prefDB;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.navigation.NavigationView;
import com.info121.iguard.AbstractActivity;
import com.info121.iguard.App;
import com.info121.iguard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.models.SiteDetail;
import com.info121.iguard.services.SmartLocationService;
import com.info121.iguard.utils.DrawableUtils;
import com.info121.iguard.utils.Util;

public class MainActivity extends AbstractActivity {





    @BindView(R.id.timer)
    TextView mTimer;

    @BindView(R.id.confirm)
    Button mConfirm;

    @BindView(R.id.acknowledge)
    Button mAcknowledge;

    @BindView(R.id.day_focus_layout)
    LinearLayout mDayFocusLayout;

    @BindView(R.id.sub_title)
    TextView mSubTitle;

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    Context mContext;

    @BindView(R.id.site_name)
    TextView siteName;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.status)
    TextView status;

    @BindView(R.id.shift)
    TextView shift;

    @BindView(R.id.shift_desc)
    TextView shiftDesc;

    @BindView(R.id.position)
    TextView position;

    @BindView(R.id.position_desc)
    TextView positionDesc;

    CountDownTimer countDownTimer;

//    @BindView(R.id.user_name)
//    TextView mUserName;

    static TextView mLastLogin, mUserName;

    JobDetail jobDetail;

    MenuItem mProfile, mNotification, mCalendar, mDeskboard, mLogout, mJobListBySite, mJobListByWeek;
    SwitchCompat mBiometricSwitch;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //   startActivity(new Intent(MainActivity.this, LoginActivity.class));

        ButterKnife.bind(this);

        mContext = MainActivity.this;

        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        mDrawerToggle.syncState();

        //mDrawerToggle.setDrawerIndicatorEnabled(false);
        //mDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_drawer);

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        List<EventDay> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Util.convertDateStringToDate("19/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(Util.convertDateStringToDate("20/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar1, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(Util.convertDateStringToDate("21/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar2, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(Util.convertDateStringToDate("22/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar3, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(Util.convertDateStringToDate("23/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar4, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(Util.convertDateStringToDate("24/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar5, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar6 = Calendar.getInstance();
        calendar6.setTime(Util.convertDateStringToDate("25/10/2022", "dd/MM/yyyy"));
        events.add(new EventDay(calendar6, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        mCalendarView.setEvents(events);

        String dateString = Util.convertDateToString(Calendar.getInstance().getTime(), "EEE dd MMM yyyy");

        //mJobDate.setText(Util.convertDateToString(Calendar.getInstance().getTime(), "dd-MMM-yyyy, EEE"));
        mSubTitle.setText(dateString);


        mCalendarView.setOnDayClickListener(eventDay -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, E");
            Calendar calendar7 = eventDay.getCalendar();

            Date selectedDate = new Date(calendar7.getTimeInMillis());
            String selectedDateString = sdf.format(selectedDate);

        //    getStartDateOfWeek(selectedDate);

            Intent intent = new Intent(MainActivity.this, JobListBySiteActivity.class);
            intent.putExtra("sDate", Util.getStartDateOfWeek(selectedDate));
            intent.putExtra("eDate", Util.getEndDateOfWeek(selectedDate));
            startActivity(intent);



        });

        mNavigationView.setItemIconTintList(null);

        View headerView = mNavigationView.getHeaderView(0);
        mLastLogin = (TextView) headerView.findViewById(R.id.last_login);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);

        mUserName.setText(App.currentUserProfile.getGuardname());
        mLastLogin.setText("Last Login : " + Util.convertDateToString(Calendar.getInstance().getTime(), "dd-MMM-yyyy hh:mm a"));

        mBiometricSwitch = (SwitchCompat) mNavigationView.getMenu().findItem(R.id.biometric_switch).getActionView().findViewById(R.id.myswitch);

        if(prefDB.getBoolean(App.CONST_USE_BIOMETRIC))
            mBiometricSwitch.setChecked(true);
        else
            mBiometricSwitch.setChecked(false);

        mBiometricSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    Log.e("biometric : ", "ON");
                    prefDB.putBoolean(App.CONST_USE_BIOMETRIC, true);
                    prefDB.putString(App.CONST_USER_NAME, App.GuardID);
                    prefDB.putString(App.CONST_PASSWORD, App.GuardPSW);

                }else{
                    Log.e("biometric : ", "OFF");
                    prefDB.putBoolean(App.CONST_USE_BIOMETRIC, false);
                }
            }
    });


        mJobListBySite = mNavigationView.getMenu().findItem(R.id.by_site);
        mJobListBySite.setOnMenuItemClickListener(menuItem -> {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, JobListBySiteActivity.class);
            intent.putExtra("TYPE", "SITE");
            startActivity(intent);
            return false;
        });

        mJobListByWeek = mNavigationView.getMenu().findItem(R.id.by_week);
        mJobListByWeek.setOnMenuItemClickListener(menuItem -> {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, JobListBySiteActivity.class);
            intent.putExtra("TYPE", "WEEK");
            startActivity(intent);
            return false;
        });


        mProfile = mNavigationView.getMenu().findItem(R.id.profile);
        mProfile.setOnMenuItemClickListener(menuItem -> {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return false;
        });

//        mJobListBySite = mNavigationView.getMenu().findItem(R.id.job_list);
//        mJobListBySite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(MainActivity.this, JobListBySiteActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });

//        mNotification = mNavigationView.getMenu().findItem(R.id.notificaiton);
//        mNotification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });

//        mCalendar = mNavigationView.getMenu().findItem(R.id.calender);
//        mCalendar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });

        mLogout = mNavigationView.getMenu().findItem(R.id.logout);
        mLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return false;
            }
        });

        startLocationService();

        getGuardJobsByWeek();



       // callSaveCheck();
    }

    private void getGuardJobsByWeek() {
        String sDateString, eDateString;

        sDateString = Util.convertDateToString(Calendar.getInstance().getTime(), "MM-dd-yyyy");
                eDateString = sDateString;


        Call<ObjectRes> call = RestClient.METRO().getApiService().GetGuardJobs(
                sDateString,
                eDateString,
                App.GuardID,
                "WEEK",
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );


        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Get guard jobs : ", "Success");

                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    if(response.body().getSiteDetails().size() > 0){
                        mDayFocusLayout.setVisibility(View.VISIBLE);
                        displayDayFocus(response.body().getSiteDetails().get(0).getJobDetails().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Get guard jobs : ", "Failed");
            }
        });

    }

    private void displayDayFocus(JobDetail jobDetail){

        this.jobDetail = jobDetail;

        final int sdk = android.os.Build.VERSION.SDK_INT;

        Date d = Util.convertDateStringToDate(jobDetail.getJobdate(), "MM/dd/yyyy");

        siteName.setText(jobDetail.getSitename());
        address.setText(jobDetail.getAddress());
        date.setText(Util.convertDateToString(d, "dd-MMM-yyyy, E"));

        shift.setText(jobDetail.getSiteShift());
        shiftDesc.setText(jobDetail.getStarttime() + "~" + jobDetail.getEndtime());

        position.setText(jobDetail.getGuardGrade());
        positionDesc.setText(jobDetail.getGuardGradeDesc());

        status.setText(jobDetail.getStatus());

        if( jobDetail.getStatus().equalsIgnoreCase("PENDING")){

           status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }else{
            status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }


        // initialize buttons
        Util.setButtonEnable(mContext, mConfirm, jobDetail.getRequireConfirmation().equalsIgnoreCase("TRUE") && !jobDetail.getStatus().equalsIgnoreCase("CONFIRMED"));
        Util.setButtonEnable(mContext, mAcknowledge, jobDetail.getRequireAcknowledgement().equalsIgnoreCase("TRUE"));


        int minutes = Math.abs(Integer.parseInt(jobDetail.getConfirmationHours()));

        countDownTimer = new CountDownTimer(Util.minutesTomillis(minutes), 1000) {
            public void onTick(long millis) {
                mTimer.setText("REMAINING : " + Util.millisToHHMMSS(millis));
            }

            public void onFinish() {
                mTimer.setText("00:00:00");
            }

        };

        if(jobDetail.getRequireConfirmation().equalsIgnoreCase("TRUE") &&  !jobDetail.getStatus().equalsIgnoreCase("CONFIRMED")) {
            mTimer.setVisibility(View.VISIBLE);
            countDownTimer.start();
        }else{
            mTimer.setVisibility(View.GONE);
        }

    }


    @OnClick(R.id.confirm)
    public void btnConfirmOnClick(){
        countDownTimer.cancel();

        Call<ObjectRes> call = RestClient.METRO().getApiService().ConfirmJob(
                App.GuardID,
                jobDetail.getJobno(),
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    Log.e("Confirm Job : ", "Success");
                    getGuardJobsByWeek();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Confirm Job : ", "Failed");
            }
        });
    }


    @OnClick(R.id.acknowledge)
    public void btnAcknowledgeOnClick(){
        countDownTimer.cancel();

        Call<ObjectRes> call = RestClient.METRO().getApiService().AcknowledgeJob(
                App.GuardID,
                jobDetail.getJobno(),
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    Log.e("Acknowledge Job : ", "Success");
                    getGuardJobsByWeek();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Acknowledge Job : ", "Failed");
            }
        });
    }





    @OnClick(R.id.checkin)
    public void checkInClick(){
        Log.e("Check in Location", getCompleteAddressString(App.location));
       // Toast.makeText(mContext, "Check In : " + getCompleteAddressString(App.location), Toast.LENGTH_LONG).show();
        App.SiteAddress = getCompleteAddressString(App.location);
        App.LogType = "Check-In";
        startActivity(new Intent(MainActivity.this, ScannerActivity.class));
    }

    @OnClick(R.id.checkout)
    public void checkOutClick(){
        Log.e("Check out Location", getCompleteAddressString(App.location));
       // Toast.makeText(mContext, "Check Out : " + getCompleteAddressString(App.location), Toast.LENGTH_LONG).show();
        App.SiteAddress = getCompleteAddressString(App.location);
        App.LogType = "Check-Out";
        startActivity(new Intent(MainActivity.this, ScannerActivity.class));

    }



    // Location related
    private void startLocationService() {
        if (isGPSEnabled()) {
            Intent serviceIntent = new Intent(MainActivity.this, SmartLocationService.class);
            MainActivity.this.startService(serviceIntent);
        }
    }

    private boolean isGPSEnabled() {

        //mContext = LoginActivity.this;

        final LocationManager manager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            alertDialog.setTitle("GPS Settings");
            alertDialog.setMessage("Your GPS/ Location service is off. \n Do you want to turn on location service?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Messages
            alertDialog.show();

            return false;
        } else
            return true;
    }

    private  String getCompleteAddressString(Location location) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null) {

                strAdd = addresses.get(0).getFeatureName();
                strAdd += ", " + addresses.get(0).getThoroughfare();
                strAdd += ", " + addresses.get(0).getLocality();
                strAdd += ", " + addresses.get(0).getCountryName();

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }





}
