package com.info121.iroster.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.info121.iroster.AbstractActivity;
import com.info121.iroster.R;
import com.info121.iroster.adapters.JobListPageAdapter;
import com.info121.iroster.adapters.SummaryPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobListActivity extends AppCompatActivity {
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.sub_title)
    TextView mSubtile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        ButterKnife.bind(this);

        final String sector = getIntent().getExtras().getString("SECTOR");

        mSubtile.setText(sector);

        // set view pager
        JobListPageAdapter pageAdapter = new JobListPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //App.test = i + "";

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


}
