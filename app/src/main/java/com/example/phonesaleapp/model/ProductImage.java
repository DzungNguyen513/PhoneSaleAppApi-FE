package com.example.phonesaleapp.model;

public class ProductImage {
    private String productImageId;
    protected String productId ;
    private String colorName ;
    private String imagePath ;
    private boolean isPrimary;

    public String getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(String productImageId) {
        this.productImageId = productImageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
