package com.example.phonesaleapp.model;

import java.util.List;

public class ColorName {
    private String colorName;
    private double colorPrice;
    private List<String> products;

    public ColorName(String colorName, double colorPrice, List<String> products) {
        this.colorName = colorName;
        this.colorPrice = colorPrice;
        this.products = products;
    }


    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public double getColorPrice() {
        return colorPrice;
    }

    public void setColorPrice(double colorPrice) {
        this.colorPrice = colorPrice;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
