package com.example.phonesaleapp.model;

public class Product {
    private String productId;
    private String productName;
    private int price;
    private int amount;
    private String imageUrl;

    public Product(String productId, String productName, int price, int amount, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


