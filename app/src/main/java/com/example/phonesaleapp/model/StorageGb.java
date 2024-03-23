package com.example.phonesaleapp.model;

import java.util.List;

public class StorageGb {
    private String storageGb;
    private double storagePrice;
    private List<String> products;

    public StorageGb(String storageGb, double storagePrice, List<String> products) {
        this.storageGb = storageGb;
        this.storagePrice = storagePrice;
        this.products = products;
    }

    public String getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(String storageGb) {
        this.storageGb = storageGb;
    }

    public double getStoragePrice() {
        return storagePrice;
    }

    public void setStoragePrice(double storagePrice) {
        this.storagePrice = storagePrice;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
