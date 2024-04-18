package com.example.phonesaleapp.model;

import java.util.List;

public class Product_Detail {
    public String productId;
    public String productName;
    public double price;
    public String imagePath;

    public Product_Detail(String productId, String productName, double price, String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.imagePath = imagePath;
    }
}
