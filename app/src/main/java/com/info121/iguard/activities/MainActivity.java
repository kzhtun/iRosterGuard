package com.info121.iguard.activities;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.models.SiteDetail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   //     startActivity(new Intent(MainActivity.this, LoginActivity.class));

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
        calendar.setTime(Util.convertDateStringToDate("25/05/2020", "dd/MM/yyyy"));
        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(Util.convertDateStringToDate("26/05/2020", "dd/MM/yyyy"));
        events.add(new EventDay(calendar1, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(Util.convertDateStringToDate("27/05/2020", "dd/MM/yyyy"));
        events.add(new EventDay(calendar2, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(Util.convertDateStringToDate("28/05/2020", "dd/MM/yyyy"));
        events.add(new EventDay(calendar3, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(Util.convertDateStringToDate("29/05/2020", "dd/MM/yyyy"));
        events.add(new EventDay(calendar4, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(Util.convertDateStringToDate("30/05/2020", "dd/MM/yyyy"));
        events.add(new EventDay(calendar5, DrawableUtils.getCircleDrawableWithText(this, "SO")));

        Calendar calendar6 = Calendar.getInstance();
        calendar6.setTime(Util.convertDateStringToDate("31/05/2020", "dd/MM/yyyy"));
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

        getGuardJobsByWeek();





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
}
