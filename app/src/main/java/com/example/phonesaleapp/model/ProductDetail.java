package com.example.phonesaleapp.model;

import android.graphics.Color;

public class ProductDetail {
    private String productDetailId;
    private String productId;
    private int storageGb;
    private String colorName;
    private Color colorNameNavigation;
    private StorageGb storageGbNavigation;


    public String getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(String productDetailId) {
        this.productDetailId = productDetailId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(int storageGb) {
        this.storageGb = storageGb;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Color getColorNameNavigation() {
        return colorNameNavigation;
    }

    public void setColorNameNavigation(Color colorNameNavigation) {
        this.colorNameNavigation = colorNameNavigation;
    }

    public StorageGb getStorageGbNavigation() {
        return storageGbNavigation;
    }

    public void setStorageGbNavigation(StorageGb storageGbNavigation) {
        this.storageGbNavigation = storageGbNavigation;
    }
}
