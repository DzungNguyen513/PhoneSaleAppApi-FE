package com.example.phonesaleapp.model;

import java.util.List;

public class Vendor {
    private String vendorId;
    private String vendorName;
    private String address;
    private String phoneNumber;
    private int status;
    private List<String> products;

    public Vendor(String vendorId, String vendorName, String address, String phoneNumber, int status, List<String> products) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.products = products;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
