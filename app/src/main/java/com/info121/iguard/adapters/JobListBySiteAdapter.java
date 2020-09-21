package com.info121.iguard.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.info121.iguard.R;
import com.info121.iguard.models.JobDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobListBySiteAdapter extends RecyclerView.Adapter<JobListBySiteAdapter.ViewHolder> {
    private Context mContext;
    List<JobDetail> mListJob;

    public JobListBySiteAdapter(Context mContext, List<JobDetail> mListJob) {
        this.mContext = mContext;
        this.mListJob = mListJob;
    }

    @NonNull
    @Override
    public JobListBySiteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View searchView = inflater.inflate(R.layout.cell_job_item_by_site, parent, false);
        return new ViewHolder(searchView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

        viewHolder.siteName.setText(mListJob.get(i).getSiteName());
        viewHolder.address.setText(mListJob.get(i).getAddress());

//        viewHolder.shift.setText(mListJob.get(i).getShift());
//        viewHolder.shiftDesc.setText(mListJob.get(i).getShiftDesc());

      //  viewHolder.position.setText(mListJob.get(i).getPost());
    //    viewHolder.positionDesc.setText(mListJob.get(i).getPostDesc());

     //   viewHolder.date.setText(mListJob.get(i).getDate());
        viewHolder.status.setText(mListJob.get(i).getStatus());

        if(mListJob.get(i).getStatus().toString().equalsIgnoreCase("CFM")){
            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }else{
            viewHolder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }


    }

    @Override
    public int getItemCount() {
        return mListJob.size();
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

//        @BindView(R.id.shift)
//        TextView shift;
//
//        @BindView(R.id.shift_desc)
//        TextView shiftDesc;

        @BindView(R.id.position)
        TextView position;

//        @BindView(R.id.position_desc)
//        TextView positionDesc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
