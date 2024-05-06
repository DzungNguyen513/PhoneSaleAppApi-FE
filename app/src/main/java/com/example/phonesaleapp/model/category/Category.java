package com.example.phonesaleapp.model.category;

import java.util.List;

public class Category {
    private String categoryId;
    private String categoryName;
    private int status;

    public Category(String categoryId, String categoryName, int status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
