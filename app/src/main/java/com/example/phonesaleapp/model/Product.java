package com.example.phonesaleapp.model;

import android.graphics.Color;

import java.util.List;

public class Product {
    private String productId;
    private String productName;
    private int amount;
    private double price;
    private String categoryId;
    private String vendorId;
    private String detail;
    private String img;
    private int status;
    private Category category;

    private Vendor vendor;
    private List<BillDetails> billDetails;
    private List<ShoppingCartDetail> shoppingCartDetails;
    private List<ProductImage> productImages;

    public String getProductId() {
        return productId;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
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



    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<BillDetails> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetails> billDetails) {
        this.billDetails = billDetails;
    }

    public List<ShoppingCartDetail> getShoppingCartDetails() {
        return shoppingCartDetails;
    }

    public void setShoppingCartDetails(List<ShoppingCartDetail> shoppingCartDetails) {
        this.shoppingCartDetails = shoppingCartDetails;
    }
}
