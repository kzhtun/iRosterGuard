package com.info121.iguard.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info121.iguard.models.JobDetail;
import com.info121.iguard.R;

import java.util.List;

import butterknife.ButterKnife;

public class AvailableRemarkAdapter extends RecyclerView.Adapter<AvailableRemarkAdapter.ViewHolder> {
    private Context mContext;
    List<JobDetail> mListJob;

    private static final int TYPE_REMARK = 0;
    private static final int TYPE_ITEM = 1;

    public AvailableRemarkAdapter(Context mContext, List<JobDetail> mListJob) {
        this.mContext = mContext;
        this.mListJob = mListJob;
    }

    @NonNull
    @Override
    public AvailableRemarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.cell_available_remark_item, parent, false);

        if (viewType == TYPE_REMARK) {
           view = inflater.inflate(R.layout.cell_remark, parent, false);
        }
//        if (viewType == TYPE_ITEM)
//        {
//            view = inflater.inflate(R.layout.cell_available_remark_item, parent, false);
//        }

        return new ViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        return (position == mListJob.size()-1) ? TYPE_REMARK : TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

    }

    @Override
    public int getItemCount() {
        return mListJob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
