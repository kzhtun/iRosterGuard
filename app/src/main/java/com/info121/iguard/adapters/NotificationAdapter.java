package com.info121.iguard.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info121.iguard.R;
import com.info121.iguard.models.Notification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context mContext;
    List<Notification> mNoti;

    public NotificationAdapter(Context mContext, List<Notification> mNotiList) {
        this.mContext = mContext;
        this.mNoti = mNotiList;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View searchView = inflater.inflate(R.layout.cell_notification, parent, false);
        return new ViewHolder(searchView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final int sdk = android.os.Build.VERSION.SDK_INT;


        if(mNoti.get(i).getType().equalsIgnoreCase("S")){
            viewHolder.icon.setImageResource(R.mipmap.ic_success);
        }else{
            viewHolder.icon.setImageResource(R.mipmap.ic_warnning);
        }

        viewHolder.title.setText(mNoti.get(i).getTitle());
        viewHolder.message.setText(mNoti.get(i).getMessage());

    }

    @Override
    public int getItemCount() {
        return mNoti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_layout)
        LinearLayout mainLayout;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.message)
        TextView message;

        @BindView(R.id.icon)
        ImageView icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
