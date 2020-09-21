package com.info121.iguard.activities;

import android.content.Context;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.info121.iguard.AbstractActivity;
import com.info121.iguard.adapters.NotificationAdapter;
import com.info121.iguard.models.Notification;
import com.info121.iguard.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AbstractActivity {
    List<Notification> mNotiList = new ArrayList<>();

    NotificationAdapter notiAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.no_data)
    TextView mNoData;

    @BindView(R.id.rv_jobs)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    Context mContext = NotificationActivity.this;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle Action bar item clicks here. The Action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);
        // set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: dummy data
        mNotiList = new ArrayList<>();


        mNotiList.add(new Notification("THERE IS A SHORTFALL FOR THE WATERBAY, TWB", "18-May-2020, TWB, THE WATERBAY needs security officers 1.\nDAY SHIFT (SHIFT 1 08:00~20:00)", "W"));
        mNotiList.add(new Notification("THERE IS A SHORTFALL FOR THE NORTHSTAR, TNR", "18-May-2020, TNR, THE NORTHSTAR needs security officers 2.\nNIGHT SHIFT (SHIFT 1 18:00~06:00)", "S"));
        mNotiList.add(new Notification("THERE IS A SHORTFALL FOR WATERWAY POINT, WWP", "18-May-2020, WWP, WATERWAY POINT needs security officers 3.\nDAY SHIFT (SHIFT 1 08:00~20:00)", "W"));



        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        notiAdapter = new NotificationAdapter(mContext, mNotiList);
        mRecyclerView.setAdapter(notiAdapter);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(false);
            }
        });

    }
}
