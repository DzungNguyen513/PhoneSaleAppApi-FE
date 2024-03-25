package com.example.phonesaleapp.model;

import java.util.List;

public class Product {
    private String productId;
    private String productName;
    private int storageGb;
    private String colorName;
    private int amount;
    private double price;
    private String categoryId;
    private String vendorId;
    private  String detail;
    private String img;
    private int status;
    private Category category;
    private ColorName colorNameNavigation;
    private StorageGb storageGbNavigation;
    private Vendor vendor;
    private List<BillDetails> billDetails;
    private List<ShoppingCartDetail> shoppingCartDetails;

    public Product(String productId, String productName, String colorName, double price, String img) {
        this.productId = productId;
        this.productName = productName;
        this.colorName = colorName;
        this.price = price;
        this.img = img;
    }

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

    public ColorName getColorNameNavigation() {
        return colorNameNavigation;
    }

    public void setColorNameNavigation(ColorName colorNameNavigation) {
        this.colorNameNavigation = colorNameNavigation;
    }

    public StorageGb getStorageGbNavigation() {
        return storageGbNavigation;
    }

    public void setStorageGbNavigation(StorageGb storageGbNavigation) {
        this.storageGbNavigation = storageGbNavigation;
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
