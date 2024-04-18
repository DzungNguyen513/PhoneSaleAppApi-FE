package com.example.phonesaleapp.model;

import java.io.Serializable;

public class ProductCart implements Serializable {
    private String shoppingCartId;
    private String productID;
    private String productName;
    private int discountedPrice;
    private int originalPrice;
    private String colorName;
    private int storageGB;
    private int amount;
    private String img;
    private boolean isSelected;

    public ProductCart(String shoppingCartId, String productId, String productName, int discountedPrice, String colorName, int storageGB, int amount, String img) {
        this.shoppingCartId = shoppingCartId;
        this.productID = productId;
        this.productName = productName;
        this.discountedPrice = discountedPrice;
        this.colorName = colorName;
        this.storageGB = storageGB;
        this.amount = amount;
        this.img = img;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }


    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getStorageGB() {
        return storageGB;
    }

    public void setStorageGB(int storageGB) {
        this.storageGB = storageGB;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


