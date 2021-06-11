package com.info121.iguard.adapters;

import android.content.Context;
import android.os.CountDownTimer;
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

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailListAdapter extends RecyclerView.Adapter<JobDetailListAdapter.ViewHolder> {
    private Context mContext;
    List<JobDetail> mJobList;

    CountDownTimer countDownTimer;

    public void update(List<JobDetail> jobList){
        this.mJobList = jobList;
        notifyDataSetChanged();
    }

    public JobDetailListAdapter(Context context, List<JobDetail> jobList) {
        this.mContext = context;
        this.mJobList = jobList ;
    }

    @NonNull
    @Override
    public JobDetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View searchView = inflater.inflate(R.layout.cell_job_item_detail, parent, false);
        return new ViewHolder(searchView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

       Date d = Util.convertDateStringToDate(mJobList.get(i).getJobdate(), "MM/dd/yyyy");

       viewHolder.siteName.setText(mJobList.get(i).getSitename());
       viewHolder.address.setText(mJobList.get(i).getAddress());
       viewHolder.date.setText(Util.convertDateToString(d, "dd-MMM-yyyy, E"));


       viewHolder.shift.setText(mJobList.get(i).getSiteShift());
        viewHolder.shiftDesc.setText(mJobList.get(i).getStarttime() + "~" + mJobList.get(i).getEndtime());

        viewHolder.position.setText(mJobList.get(i).getGuardGrade());
        viewHolder.positionDesc.setText(mJobList.get(i).getGuardGradeDesc());

        viewHolder.status.setText(mJobList.get(i).getStatus());

        if( mJobList.get(i).getStatus().equalsIgnoreCase("PENDING")){

            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }else{
            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }

        // initialize buttons
        Util.setButtonEnable(mContext, viewHolder.mConfirm, mJobList.get(i).getRequireConfirmation().equalsIgnoreCase("TRUE") && !mJobList.get(i).getStatus().equalsIgnoreCase("CONFIRMED"));


        Util.setButtonEnable(mContext, viewHolder.mAcknowledge, mJobList.get(i).getRequireAcknowledgement().equalsIgnoreCase("TRUE"));

        int minutes = Math.abs(Integer.parseInt(mJobList.get(i).getConfirmationHours()));

          countDownTimer =  new CountDownTimer(Util.minutesTomillis(minutes), 1000) {
                public void onTick(long millis) {
                    viewHolder.mTimer.setText("REMAINING : " + Util.millisToHHMMSS(millis));
                }

                public void onFinish() {
                    viewHolder.mTimer.setText("00:00:00");
                }

            };

        if( mJobList.get(i).getRequireConfirmation().equalsIgnoreCase("TRUE") && !mJobList.get(i).getStatus().equalsIgnoreCase("CONFIRMED")) {
            viewHolder.mTimer.setVisibility(View.VISIBLE);
            countDownTimer.start();
        }else{
            viewHolder.mTimer.setVisibility(View.GONE);
        }


        viewHolder.mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClick(mJobList.get(i).getJobno());
            }
        });

        viewHolder.mAcknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acknowledgeClick(mJobList.get(i).getJobno());
            }
        });

    }



    public void confirmClick(String jobNo){
        countDownTimer.cancel();

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
        countDownTimer.cancel();

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

    @Override
    public int getItemCount() {
        return mJobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.timer)
        TextView mTimer;

        @BindView(R.id.confirm)
        Button mConfirm;

        @BindView(R.id.acknowledge)
        Button mAcknowledge;

        @BindView(R.id.site_name)
        TextView siteName;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.shift)
        TextView shift;

        @BindView(R.id.shift_desc)
        TextView shiftDesc;

        @BindView(R.id.position)
        TextView position;

        @BindView(R.id.position_desc)
        TextView positionDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
