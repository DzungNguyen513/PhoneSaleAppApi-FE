package com.example.phonesaleapp.api.request.shoppingcartitems;

public class UpdateAmountRequest {
    private String shoppingCartId;
    private String productId;
    private int newAmount;

    public UpdateAmountRequest(String shoppingCartId, String productId, int newAmount) {
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.newAmount = newAmount;
    }
}