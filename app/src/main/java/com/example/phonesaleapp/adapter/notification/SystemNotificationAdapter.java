package com.example.phonesaleapp.adapter.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;

import com.example.phonesaleapp.R;
import com.example.phonesaleapp.view.notification.NotificationDetailActivity;
import com.example.phonesaleapp.model.systemnotification.SystemNotification;

import java.util.List;

public class SystemNotificationAdapter extends ArrayAdapter<SystemNotification> {
    public SystemNotificationAdapter(@NonNull Context context, int resource, @NonNull List<SystemNotification> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
        }

        SystemNotification noti = getItem(position);

        TextView tv_notiTitle = listItemView.findViewById(R.id.tv_notiTitle);
        TextView tv_notiDescription = listItemView.findViewById(R.id.tv_notiDescription);
        TextView tv_dateNoti = listItemView.findViewById(R.id.tv_dateNoti);
        TextView tv_viewDetailNoti = listItemView.findViewById(R.id.tv_viewDetailNoti);

        if (noti != null) {
            Spanned formattedTitle = Html.fromHtml(noti.getTitle(), Html.FROM_HTML_MODE_LEGACY);
            tv_notiTitle.setText(formattedTitle);
            Spanned formattedDescription = Html.fromHtml(noti.getDescription(), Html.FROM_HTML_MODE_LEGACY);
            tv_notiDescription.setText(formattedDescription);
            tv_dateNoti.setText(noti.getCreatedAt());
        }

        tv_viewDetailNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotificationDetailActivity.class);
                intent.putExtra("notification", noti);
                getContext().startActivity(intent);
            }
        });

        return listItemView;
    }
}

