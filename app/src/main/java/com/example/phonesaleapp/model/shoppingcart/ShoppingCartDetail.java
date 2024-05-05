package com.example.phonesaleapp.model.shoppingcart;

public class ShoppingCartDetail {
    private String shoppingCartId;
    private String productId;
    private String colorName;
    private int storageGb;
    private Integer amount;
    private Integer total;

    public ShoppingCartDetail(String shoppingCartId, String productId, String colorName, int storageGb, Integer amount, Integer total) {
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.colorName = colorName;
        this.storageGb = storageGb;
        this.amount = amount;
        this.total = total;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(int storageGb) {
        this.storageGb = storageGb;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
