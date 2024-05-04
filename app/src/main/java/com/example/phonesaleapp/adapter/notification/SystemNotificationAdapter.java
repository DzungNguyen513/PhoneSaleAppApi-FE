package com.example.phonesaleapp.adapter.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.model.systemnotification.SystemNotification;

import org.w3c.dom.Text;

import java.util.List;

public class SystemNotificationAdapter extends RecyclerView.Adapter<SystemNotificationAdapter.ViewHolder> {
    private Context context;
    private List<SystemNotification> lstNoti;

    public SystemNotificationAdapter(Context context, List<SystemNotification> lstNoti) {
        this.context = context;
        this.lstNoti = lstNoti;
    }

    @NonNull
    @Override
    public SystemNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SystemNotificationAdapter.ViewHolder holder, int position) {
        SystemNotification noti = lstNoti.get(position);
        holder.tv_notiTitle.setText(noti.getTitle());
        holder.tv_notiContent.setText(noti.getMessage());
        holder.tv_dateNoti.setText(noti.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return lstNoti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notiTitle, tv_notiContent, tv_dateNoti, tv_viewDetailNoti;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_notiTitle = itemView.findViewById(R.id.tv_notiTitle);
            tv_notiContent = itemView.findViewById(R.id.tv_notiContent);
            tv_dateNoti = itemView.findViewById(R.id.tv_dateNoti);
            tv_viewDetailNoti = itemView.findViewById(R.id.tv_viewDetailNoti);
        }
    }
}
