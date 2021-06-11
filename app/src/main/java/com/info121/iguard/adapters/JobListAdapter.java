package com.info121.iguard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.info121.iguard.R;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.utils.Util;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobListAdapter extends BaseAdapter {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<JobDetail> mJobList = new ArrayList<>();
    Context mContext;

    public JobListAdapter(List<JobDetail> jobs, Context context) {
        this.mJobList = jobs;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mJobList.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (i == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cell_job_header, null);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.cell_job_item, null);
        }


        if (i > 0) {

            TextView sr = view.findViewById(R.id.sr);
            TextView date = view.findViewById(R.id.date);
            TextView position = view.findViewById(R.id.position);
            TextView status = view.findViewById(R.id.status);
            CheckBox checkBox = view.findViewById(R.id.select);

            JobDetail jd = mJobList.get(i - 1);

            Date d = Util.convertDateStringToDate(jd.getJobdate(), "MM/dd/yyyy");

            sr.setText(i + ".");
            date.setText(Util.convertDateToString(d, "dd-MMM-yyyy, E"));
            position.setText(jd.getGuardGrade());

            if (jd.getStatus().equalsIgnoreCase("PENDING")) {
                status.setText("PND");
                status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            } else {
                status.setText("CFM");
                status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            }


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked())
                        mJobList.get(i-1).setChecked(true);
                    else
                        mJobList.get(i-1).setChecked(false);
                }
            });

        }

//
//        user.setText(devices.get(i).getGuardname());
//        deviceId.setText(devices.get(i).gethPNo());


        return view;
    }

}
