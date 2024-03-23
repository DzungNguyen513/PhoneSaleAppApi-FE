package com.example.phonesaleapp.model;

import java.util.List;

public class Category {
    private String categoryId;
    private String categoryName;
    private int status;
    private List<String> products;

    public Category(String categoryId, String categoryName, int status, List<String> products) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
        this.products = products;
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

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
