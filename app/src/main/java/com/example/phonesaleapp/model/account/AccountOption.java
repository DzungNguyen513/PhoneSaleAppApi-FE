package com.example.phonesaleapp.model.account;

public class AccountOption {
    private String title;
    private int icon;

    public AccountOption(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
