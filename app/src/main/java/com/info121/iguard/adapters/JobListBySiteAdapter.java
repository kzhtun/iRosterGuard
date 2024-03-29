package com.info121.iguard.adapters;

import android.content.Context;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.info121.iguard.App;
import com.info121.iguard.R;
import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.models.SiteDetail;
import com.info121.iguard.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListBySiteAdapter extends RecyclerView.Adapter<JobListBySiteAdapter.ViewHolder> {
    private Context mContext;
    List<SiteDetail> mSiteList;


    public void update(List<SiteDetail> siteList){
        this.mSiteList = siteList;
        notifyDataSetChanged();
    }

    public JobListBySiteAdapter(Context context, List<SiteDetail> siteList) {
        this.mContext = context;
        this.mSiteList = siteList;
    }

    @NonNull
    @Override
    public JobListBySiteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View searchView = inflater.inflate(R.layout.cell_job_group, parent, false);
        return new ViewHolder(searchView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

        viewHolder.siteName.setText(mSiteList.get(i).getSitename());
        viewHolder.address.setText(mSiteList.get(i).getJobDetails().get(0).getAddress());


        JobListAdapter deviceListAdapter = new JobListAdapter(mSiteList.get(i).getJobDetails(), mContext);
        viewHolder.listJob.setAdapter(deviceListAdapter);

        setListViewHeightBasedOnItems(viewHolder.listJob);


        viewHolder.mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(JobDetail jobDetail: mSiteList.get(i).getJobDetails()){
                    Log.e(jobDetail.getJobno(), jobDetail.getChecked().toString());

                    if(jobDetail.getChecked())
                        confirmClick(jobDetail.getJobno());
                }
                refreshJob();
            }
        });

        viewHolder.mAcknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(JobDetail jobDetail: mSiteList.get(i).getJobDetails()){
                    Log.e(jobDetail.getJobno(), jobDetail.getChecked().toString());
                    if(jobDetail.getChecked())
                        acknowledgeClick(jobDetail.getJobno());
                }
                refreshJob();
            }
        });

//        viewHolder.status.setText(mListJob.get(i).getStatus());
//
//        if(mListJob.get(i).getStatus().toString().equalsIgnoreCase("CFM")){
//            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
//        }else{
//            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//        }
    }

    public void confirmClick(String jobNo){

        Call<ObjectRes> call = RestClient.METRO().getApiService().ConfirmJob(
                App.GuardID,
                jobNo,
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    Log.e("Confirm Job : ", "Success");
                    EventBus.getDefault().post("REFRESH_JOBS");
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Confirm Job : ", "Failed");
            }
        });
    }

    public void acknowledgeClick(String jobNo){

        Call<ObjectRes> call = RestClient.METRO().getApiService().AcknowledgeJob(
                App.GuardID,
                jobNo,
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                if (response.body().getResponsemessage().equalsIgnoreCase("Success")) {
                    Log.e("Acknowledge Job : ", "Success");
                    EventBus.getDefault().post("REFRESH_JOBS");
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Acknowledge Job : ", "Failed");
            }
        });
    }

    private void refreshJob(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post("REFRESH_JOBS");
            }
        }, 3000);
    }


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    @Override
    public int getItemCount() {
        return mSiteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.site_name)
        TextView siteName;

        @BindView(R.id.list_job)
        ListView listJob;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.confirm)
        Button mConfirm;

        @BindView(R.id.acknowledge)
        Button mAcknowledge;
//
//        @BindView(R.id.date)
//        TextView date;
//
//        @BindView(R.id.status)
//        TextView status;
//
////        @BindView(R.id.shift)
////        TextView shift;
////
////        @BindView(R.id.shift_desc)
////        TextView shiftDesc;
//
//        @BindView(R.id.position)
//        TextView position;

//        @BindView(R.id.position_desc)
//        TextView positionDesc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
