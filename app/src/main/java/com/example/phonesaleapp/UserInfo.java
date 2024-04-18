package com.example.phonesaleapp;

public class UserInfo {
    private static UserInfo instance;
    private String email;
    private UserInfo(){

    }
    public static synchronized UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
