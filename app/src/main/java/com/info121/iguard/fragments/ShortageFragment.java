package com.info121.iguard.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.info121.iguard.R;
import com.info121.iguard.adapters.ShortageAdapter;
import com.info121.iguard.models.JobDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShortageFragment extends Fragment {
    List<JobDetail> mJobList = new ArrayList<>();

    ShortageAdapter shortageAdapter;

    Context mContext = getActivity();
    String mCurrentTab = "";

    @BindView(R.id.no_data)
    TextView mNoData;

    @BindView(R.id.rv_jobs)
    RecyclerView mRecyclerView;

    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeLayout;

    public ShortageFragment() {
        // Required empty public constructor
    }

    public static ShortageFragment newInstance(String param1) {
        ShortageFragment fragment = new ShortageFragment();
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
        View view = inflater.inflate(R.layout.fragment_shortage, container, false);

        ButterKnife.bind(this, view);

        //TODO: dummy data
//        mJobList = new ArrayList<>();
//
//        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR (TNR)", "SHIFT 1 (08:00~20:00)", "SSO", "PENDING", "OFFICER: MOHD RAFER BIN JAMAT"));
//        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR (TNR)", "SHIFT 1 (08:00~20:00)", "SSO", "PENDING", "OFFICER: MOHD RAFER BIN JAMAT"));
//        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR (TNR)", "SHIFT 1 (08:00~20:00)", "SSO", "PENDING", "OFFICER: MOHD RAFER BIN JAMAT"));
//        mJobList.add(new JobDetail("CONTRACT #02190", "THE NORTH STAR (TNR)", "SHIFT 1 (08:00~20:00)", "SSO", "PENDING", "OFFICER: MOHD RAFER BIN JAMAT"));
//
//
//
//        mRecyclerView.setHasFixedSize(false);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//        shortageAdapter = new ShortageAdapter(mContext, mJobList);
//        mRecyclerView.setAdapter(shortageAdapter);

//        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getRelatedTabData();
//            }
//        });

        return view;
    }
}
