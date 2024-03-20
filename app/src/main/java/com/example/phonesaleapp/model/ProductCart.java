package com.example.phonesaleapp.model;

public class ProductCart {
    private String shoppingCartId;
    private String productID;
    private String productName;
    private int price;
    private int amount;
    private String img;

    private boolean isSelected;

    public ProductCart(String shoppingCartId, String productId, String productName, int price, int amount, String img) {
        this.shoppingCartId = shoppingCartId;
        this.productID = productId;
        this.productName = productName;
        this.price = price;
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


