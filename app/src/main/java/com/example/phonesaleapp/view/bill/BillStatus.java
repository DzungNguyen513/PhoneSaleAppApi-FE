package com.example.phonesaleapp.view.bill;

public enum BillStatus {
    ChoXacNhan("0"),
    ChoLayHang("1"),
    ChoGiaoHang("2"),
    DaGiao("3"),
    DaHuy("4");

    private String value;

    BillStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}