package com.example.phonesaleapp.model.shoppingcart;

public class ShoppingCart {
    private String shoppingCartId;

    private String customerId;

    private int totalCart;

    private int status;

    private String createAt;

    private String updateAt;

    public ShoppingCart(String shoppingCartId, String customerId, int totalCart, int status) {
        this.shoppingCartId = shoppingCartId;
        this.customerId = customerId;
        this.totalCart = totalCart;
        this.status = status;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(int totalCart) {
        this.totalCart = totalCart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
