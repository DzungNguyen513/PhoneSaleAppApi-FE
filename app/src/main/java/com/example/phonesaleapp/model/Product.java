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
}
