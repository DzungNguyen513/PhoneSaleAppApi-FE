package com.example.phonesaleapp.model;

import java.util.List;

public class Product_Detail {
    public String productId;
    public String productName;
    public String colorName;
    public double price;
    public String imagePath;

    public Product_Detail(String productId, String productName, String colorName, double price, String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.colorName = colorName;
        this.price = price;
        this.imagePath = imagePath;
    }
}
