package com.example.phonesaleapp.model.customer;

public class TokenUpdateDTO {
    private String notificationToken;

    public TokenUpdateDTO(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }
}
