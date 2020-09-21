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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.info121.iguard.AbstractActivity;
import com.info121.iguard.App;
import com.info121.iguard.R;
import com.info121.iguard.adapters.JobListAdapter;
import com.info121.iguard.adapters.JobListBySiteAdapter;
import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.utils.Util;

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

    List<JobDetail> mJobListBySite = new ArrayList<>();
    List<JobDetail> mJobList = new ArrayList<>();

    JobListBySiteAdapter jobListBySiteAdapter;
    JobListAdapter jobListAdapter;

    @BindView(R.id.no_data)
    TextView mNoData;

    @BindView(R.id.rv_jobs)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.sub_title)
    TextView mSubtile;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.view_switch)
    ImageView mViewSwitch;


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
    public void backOnClick(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_by_site);

        ButterKnife.bind(this);
        // set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final String sector = getIntent().getExtras().getString("DATE");

        //     mSubtile.setText(sector);

        //TODO: dummy data
        mJobListBySite = new ArrayList<>();
        mJobListBySite.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "", "PND", "MOHD RAFER BIN"));
        mJobListBySite.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Serinor Office", "", "CFM", "MOHD RAFER BIN"));
        mJobListBySite.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Serinor Office", "", "PND", "MOHD RAFER BIN"));


        mJobList = new ArrayList<>();
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "25-May-2020, Mon", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "26-May-2020, Tue", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "27-May-2020, Wed", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "28-May-2020, Thu", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "29-May-2020, Fri", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "30-May-2020, Sat", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR, TNR", "6 Battery Rd, Singapore 049909", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "31-May-2020, Sun", "PENDING", "MOHD RAFER BIN"));

        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "S0", "Security Office", "25-May-2020, Mon", "CONFIRMED", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "26-May-2020, Tue", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "27-May-2020, Wed", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "28-May-2020, Thu", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "29-May-2020, Fri", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "30-May-2020, Sat", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #04100", "WATERWAY POINT, WWP", "83 Punggol Central, Singapore 828761", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "31-May-2020, Sun", "PENDING", "MOHD RAFER BIN"));


        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "25-May-2020, Mon", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "26-May-2020, Tue", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "27-May-2020, Wed", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "28-May-2020, Thu", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "29-May-2020, Fri", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "30-May-2020, Sat", "PENDING", "MOHD RAFER BIN"));
        mJobList.add(new JobDetail("CONTRACT #30933", "THE WATERBAY, TWB", "45 Edgefield Plains, Singapore 828710", "SHIFT 1", "(08:00~20:00)", "SO", "Security Office", "31-May-2020, Sun", "PENDING", "MOHD RAFER BIN"));


        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        jobListBySiteAdapter = new JobListBySiteAdapter(mContext, mJobListBySite);
        jobListAdapter = new JobListAdapter(mContext, mJobList);


        mRecyclerView.setAdapter(jobListBySiteAdapter);


        mViewSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
//                String selectedDate = sdf.format(new Date(Calendar.getInstance().getTimeInMillis()));
//
//                Intent intent = new Intent(JobListBySiteActivity.this, JobListActivity.class);
//                intent.putExtra("DATE", selectedDate);
//
//                startActivity(intent);
//                finish();




                if (mRecyclerView.getAdapter() instanceof JobListAdapter) {
                    mRecyclerView.setAdapter(jobListBySiteAdapter);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_list, getApplicationContext().getTheme()));
                    } else {
                        mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_list));
                    }

                } else {
                    mRecyclerView.setAdapter(jobListAdapter);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_group, getApplicationContext().getTheme()));
                    } else {
                        mViewSwitch.setImageDrawable(getResources().getDrawable(R.mipmap.ic_group));
                    }
                }
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(false);
            }
        });
    }



    private void getGuardJobs(){
        Call<ObjectRes> call = RestClient.METRO().getApiService().GetGuardJobs(
                "09-14-2020",
                "09-20-2020",
                App.GuardID,
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );


        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Get guard jobs : ", "Success");



            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Get guard jobs : ", "Failed");
            }
        });

    }



}
