package com.example.phonesaleapp.model.systemnotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemNotification {
    private String title;
    private String message;
    private String notificationType;
    private boolean isActive;
    private String createdAt;

    public SystemNotification(String title, String message, String notificationType, boolean isActive, String createdAt) {
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedAt() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = inputFormat.parse(createdAt);
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
