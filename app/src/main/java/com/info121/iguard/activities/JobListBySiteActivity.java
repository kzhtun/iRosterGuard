package com.info121.iguard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.info121.iguard.AbstractActivity;
import com.info121.iguard.App;
import com.info121.iguard.R;
import com.info121.iguard.adapters.JobDetailListAdapter;
import com.info121.iguard.adapters.JobListBySiteAdapter;
import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.models.SiteDetail;
import com.info121.iguard.utils.Util;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListBySiteActivity extends AbstractActivity {
    Context mContext = JobListBySiteActivity.this;

    List<SiteDetail> mSiteList = new ArrayList<>();
    List<JobDetail> mJobList = new ArrayList<>();

    JobListBySiteAdapter jobListBySiteAdapter;
    JobDetailListAdapter jobDetailListAdapter;

    @BindView(R.id.no_data)
    TextView mNoData;

    @BindView(R.id.rv_jobs)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.sub_title)
    TextView mSubtile;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.view_switch)
    ImageView mViewSwitch;

    String sDateString, eDateString;
    Date sDate, eDate;

    Boolean isGroup = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }


    @OnClick(R.id.prev)
    public void prevOnClick() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate); // Util.convertDateStringToDate("25/05/2020", "dd/MM/yyyy"));

        cal.add(Calendar.DATE, -7);

        loadPrevNextWeekJobs(cal);
    }

    @OnClick(R.id.next)
    public void nextOnClick() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate); // Util.convertDateStringToDate("25/05/2020", "dd/MM/yyyy"));

        cal.add(Calendar.DATE, 7);

        loadPrevNextWeekJobs(cal);
    }

    private void loadPrevNextWeekJobs(Calendar cal) {
        sDateString = Util.getStartDateOfWeek(cal.getTime());
        eDateString = Util.getEndDateOfWeek(cal.getTime());

        sDate = Util.convertDateStringToDate(sDateString, "MM-dd-yyyy");
        eDate = Util.convertDateStringToDate(eDateString, "MM-dd-yyyy");

        mSwipeLayout.setRefreshing(true);
        getGuardJobsBySite();
        getGuardJobsByWeek();

        Log.e("sDate : ", sDateString);
        Log.e("eDate : ", eDateString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_by_site);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                if (extras.getString("TYPE") != null) {
                    if (extras.getString("TYPE").equalsIgnoreCase("WEEK")) {
                        isGroup = false;
                        sDateString = Util.getStartDateOfWeek(new Date());
                        eDateString = Util.getEndDateOfWeek(new Date());
                        mTitle.setText("JOB LIST BY WEEK");
                    }

                    if (extras.getString("TYPE").equalsIgnoreCase("SITE")) {
                        isGroup = true;
                        sDateString = Util.getStartDateOfWeek(new Date());
                        eDateString = Util.getEndDateOfWeek(new Date());
                        mTitle.setText("JOB LIST BY SITE");
                    }
                } else {
                    sDateString = extras.getString("sDate");
                    eDateString = extras.getString("eDate");

                }
            }
        }

        sDate = Util.convertDateStringToDate(sDateString, "MM-dd-yyyy");
        eDate = Util.convertDateStringToDate(eDateString, "MM-dd-yyyy");


        // set toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        jobListBySiteAdapter = new JobListBySiteAdapter(mContext, mSiteList);
        jobDetailListAdapter = new JobDetailListAdapter(mContext, mJobList);


        // data loading
        mSwipeLayout.setRefreshing(true);
        getGuardJobsBySite();
        getGuardJobsByWeek();

        mSwipeLayout.setOnRefreshListener(() -> {
            mSwipeLayout.setRefreshing(true);
            getGuardJobsBySite();
            getGuardJobsByWeek();
        });



        mSubtile.setText(Util.convertDateToString(sDate, "dd MMM") + " ~ " + Util.convertDateToString(eDate, "dd MMM"));

        mViewSwitch.setOnClickListener(view -> {

            if (isGroup) {
                isGroup = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_group, getApplicationContext().getTheme()));
                } else {
                    mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_group));
                }

                mTitle.setText("JOB LIST BY WEEK");
                mRecyclerView.setAdapter(jobDetailListAdapter);

            } else {
                isGroup = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_list, getApplicationContext().getTheme()));
                } else {
                    mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_list));
                }

                mTitle.setText("JOB LIST BY SITE");
                mRecyclerView.setAdapter(jobListBySiteAdapter);
            }
        });


        if (isGroup)
            mRecyclerView.setAdapter(jobListBySiteAdapter);
        else
            mRecyclerView.setAdapter(jobDetailListAdapter);

    }


    private void getGuardJobsBySite() {

        mSubtile.setText(Util.convertDateToString(sDate, "dd MMM") + " ~ " + Util.convertDateToString(eDate, "dd MMM"));

        Call<ObjectRes> call = RestClient.METRO().getApiService().GetGuardJobs(
                sDateString,
                eDateString,
                App.GuardID,
                "SITE",
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );


        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Get guard jobs : ", "Success");

                mSwipeLayout.setRefreshing(false);
                mSiteList = new ArrayList<>();
                mJobList = new ArrayList<>();

                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {

                    mSiteList = response.body().getSiteDetails();
                    // site list
                    jobListBySiteAdapter.update(mSiteList);


                    if (mSiteList.size() > 0) {
                        mNoData.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }


                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                mSwipeLayout.setRefreshing(false);
                Log.e("Get guard jobs : ", "Failed");
            }
        });

    }

    private void getGuardJobsByWeek() {

        mSubtile.setText(Util.convertDateToString(sDate, "dd MMM") + " ~ " + Util.convertDateToString(eDate, "dd MMM"));

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

                mSwipeLayout.setRefreshing(false);
                mSiteList = new ArrayList<>();
                mJobList = new ArrayList<>();

                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {

                    mSiteList = response.body().getSiteDetails();

                    for (SiteDetail s : mSiteList) {
                        mJobList.addAll(s.getJobDetails());
                    }

                    // job detail list
                    jobDetailListAdapter.update(mJobList);

                    if (mSiteList.size() > 0) {
                        mNoData.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                mSwipeLayout.setRefreshing(false);
                Log.e("Get guard jobs : ", "Failed");
            }
        });
    }


    @Subscribe
    public void onEvent(String action) {
        if(action.equalsIgnoreCase("REFRESH_JOBS")){
            getGuardJobsBySite();
            getGuardJobsByWeek();
            Log.e("Refresh Jobs : ", "Success");
        }
    }
}
