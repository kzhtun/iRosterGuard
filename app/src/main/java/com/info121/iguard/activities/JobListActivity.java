package com.info121.iguard.activities;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.info121.iguard.R;
import com.info121.iguard.adapters.JobListAdapter;
import com.info121.iguard.adapters.JobListPageAdapter;
import com.info121.iguard.models.JobDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobListActivity extends AppCompatActivity {
    Context mContext = JobListActivity.this;

    List<JobDetail> mJobList = new ArrayList<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        ButterKnife.bind(this);
        // set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String dateString = getIntent().getExtras().getString("DATE");

       // mSubtile.setText(dateString);

        //TODO: dummy data
        mJobList = new ArrayList<>();

        mJobList.add(new JobDetail("CONTRACT #02190","THE NORTH STAR (TNR)", "TNR address, Singapore 002022", "SHIFT 1", "(08:00~20:00)",  "SSO", "Serinor Security Office", dateString, "PENDING", "MOHD RAFER BIN" ));
        mJobList.add(new JobDetail("CONTRACT #02190","Water Way Point, WWP", "Water way address, Singpore 93882","SHIFT 1", "(08:00~20:00)",  "SSO", "Serinor Security Office", dateString, "CONFIRM", "MOHD RAFER BIN" ));
        mJobList.add(new JobDetail("CONTRACT #02190","HASAN  ABDULLA  ALSERARI", "Hasan, Singapore 570108","SHIFT 1", "(08:00~20:00)",  "SSO", "Serinor Security Office", dateString, "PENDING", "MOHD RAFER BIN" ));
        mJobList.add(new JobDetail("CONTRACT #02190","THE NORTH STAR (TNR)", "45 EdqeField Plains, Singapore 570108","SHIFT 1", "(08:00~20:00)",  "SSO", "Serinor Security Office", dateString, "PENDING", "MOHD RAFER BIN" ));


        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        jobListAdapter = new JobListAdapter(mContext, mJobList);
        mRecyclerView.setAdapter(jobListAdapter);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(false);
            }
        });


        mViewSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, E");
                String selectedDate = sdf.format(new Date(Calendar.getInstance().getTimeInMillis()));

                Intent intent = new Intent(JobListActivity.this, JobListBySiteActivity.class);
                intent.putExtra("DATE", selectedDate);

                startActivity(intent);
              //  finish();
            }
        });

    }


    private void getJobLists(){

    }


}
