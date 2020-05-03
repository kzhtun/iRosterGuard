package com.info121.iroster.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.info121.iroster.R;
import com.info121.iroster.adapters.SummaryAdapter;
import com.info121.iroster.models.JobSummary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShiftFragment extends Fragment {
    List<JobSummary> mJobList = new ArrayList<>();

    SummaryAdapter summaryAdapter;

    Context mContext = getActivity();
    String mCurrentTab = "";

    @BindView(R.id.no_data)
    TextView mNoData;

    @BindView(R.id.rv_jobs)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    public ShiftFragment() {
        // Required empty public constructor
    }

    public static ShiftFragment newInstance(String param1) {
        ShiftFragment fragment = new ShiftFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        fragment.mCurrentTab = param1;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shift, container, false);


        ButterKnife.bind(this, view);



        //TODO: dummy data
        mJobList = new ArrayList<>();

        mJobList.add(new JobSummary("NORTH", "-3"));
        mJobList.add(new JobSummary("WEST", "+3"));
        mJobList.add(new JobSummary("EAST", "0"));
        mJobList.add(new JobSummary("CENTRAL", "-5"));

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        summaryAdapter = new SummaryAdapter(mContext, mJobList);
        mRecyclerView.setAdapter(summaryAdapter);

//        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getRelatedTabData();
//            }
//        });

        return view;
    }
}
