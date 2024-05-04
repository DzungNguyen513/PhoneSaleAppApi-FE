package com.example.phonesaleapp.model.systemnotification;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemNotification implements Serializable {
    private String notificationName;
    private String title;
    private String description;
    private String message;
    private String notificationType;
    private boolean isActive;
    private String createdAt;

    public SystemNotification(String notificationName, String title, String description, String message, String notificationType, boolean isActive, String createdAt) {
        this.notificationName = notificationName;
        this.title = title;
        this.description = description;
        this.message = message;
        this.notificationType = notificationType;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
