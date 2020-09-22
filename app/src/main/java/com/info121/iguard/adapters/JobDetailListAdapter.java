package com.info121.iguard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.info121.iguard.R;
import com.info121.iguard.models.JobDetail;
import com.info121.iguard.models.SiteDetail;
import com.info121.iguard.utils.Util;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobDetailListAdapter extends RecyclerView.Adapter<JobDetailListAdapter.ViewHolder> {
    private Context mContext;
    List<JobDetail> mJobList;

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


        if( mJobList.get(i).getStatus().equalsIgnoreCase("PENDING")){
            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }else{
            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }

    }



    @Override
    public int getItemCount() {
        return mJobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
