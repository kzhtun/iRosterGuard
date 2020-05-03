package com.info121.iroster.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.iroster.R;
import com.info121.iroster.activities.JobDetailActivity;
import com.info121.iroster.activities.JobListActivity;
import com.info121.iroster.models.JobDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShortageAdapter extends RecyclerView.Adapter<ShortageAdapter.ViewHolder> {
    private Context mContext;
    List<JobDetail> mListJob;

    public ShortageAdapter(Context mContext, List<JobDetail> mListJob) {
        this.mContext = mContext;
        this.mListJob = mListJob;
    }

    @NonNull
    @Override
    public ShortageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View searchView = inflater.inflate(R.layout.cell_job_item, parent, false);
        return new ViewHolder(searchView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

        viewHolder.siteId.setText(mListJob.get(i).getId());
        viewHolder.siteName.setText(mListJob.get(i).getSiteName());
        viewHolder.shift.setText(mListJob.get(i).getShift());
        viewHolder.post.setText(mListJob.get(i).getPost());
        viewHolder.status.setText(mListJob.get(i).getStatus());
        viewHolder.officer.setText(mListJob.get(i).getOfficer());


        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                intent.putExtra("SITENAME", viewHolder.siteName.getText());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_layout)
        LinearLayout mainLayout;

        @BindView(R.id.site_id)
        TextView siteId;

        @BindView(R.id.site_name)
        TextView siteName;

        @BindView(R.id.shift)
        TextView shift;

        @BindView(R.id.post)
        TextView post;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.officer)
        TextView officer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
