package com.info121.iroster.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.iroster.R;
import com.info121.iroster.activities.JobListActivity;
import com.info121.iroster.models.JobSummary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private Context mContext;
    List<JobSummary> mListJob;

    public SummaryAdapter(Context mContext, List<JobSummary> mListJob) {
        this.mContext = mContext;
        this.mListJob = mListJob;
    }

    @NonNull
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View searchView = inflater.inflate(R.layout.cell_summary, parent, false);
        return new ViewHolder(searchView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

            switch (mListJob.get(i).getSector()){
                case "NORTH" :
                    viewHolder.mainLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_north));
                    break;
                case "WEST" :
                    viewHolder.mainLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_west));
                    break;
                case "EAST" :
                    viewHolder.mainLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_east));
                    break;
                case "CENTRAL":
                    viewHolder.mainLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_central));
                    break;

            }


        } else {

            switch (mListJob.get(i).getSector()){
                case "NORTH" :
                    viewHolder.mainLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_north));
                    break;
                case "WEST" :
                    viewHolder.mainLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_west));
                    break;
                case "EAST" :
                    viewHolder.mainLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_east));
                    break;
                case "CENTRAL":
                    viewHolder.mainLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_layout_central));
                    break;

            }

        }



        viewHolder.sector.setText(mListJob.get(i).getSector());
        viewHolder.count.setText(mListJob.get(i).getCount());

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, JobListActivity.class);
                intent.putExtra("SECTOR", viewHolder.sector.getText());

                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListJob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_layout)
        LinearLayout mainLayout;

        @BindView(R.id.sector)
        TextView sector;

        @BindView(R.id.job_count)
        TextView count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
