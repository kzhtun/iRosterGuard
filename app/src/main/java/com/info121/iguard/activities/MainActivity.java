package com.info121.iguard.activities;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import com.info121.iguard.utils.DrawableUtils;
import com.info121.iguard.utils.Util;

public class MainActivity extends AbstractActivity {
    @BindView(R.id.job_date)
    TextView mJobDate;

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

    static TextView mLastLogin, mUserName;

    MenuItem mProfile, mNotification, mCalendar, mDeskboard, mLogout, mJobListBySite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   //     startActivity(new Intent(MainActivity.this, LoginActivity.class));

        ButterKnife.bind(this);

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

        mJobDate.setText(Util.convertDateToString(Calendar.getInstance().getTime(), "dd-MMM-yyyy, EEE"));
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

        mProfile = mNavigationView.getMenu().findItem(R.id.profile);
        mProfile.setOnMenuItemClickListener(menuItem -> {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return false;
        });

        mJobListBySite = mNavigationView.getMenu().findItem(R.id.job_list);
        mJobListBySite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, JobListBySiteActivity.class);
                startActivity(intent);
                return false;
            }
        });

        mNotification = mNavigationView.getMenu().findItem(R.id.notificaiton);
        mNotification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
                return false;
            }
        });

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
    }




}
