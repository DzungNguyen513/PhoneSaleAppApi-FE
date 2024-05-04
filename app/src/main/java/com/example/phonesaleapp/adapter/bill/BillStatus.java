package com.example.phonesaleapp.adapter.bill;

public enum BillStatus {
    ChoXacNhan(0, "Chờ Xác Nhận"),
    ChoLayHang(1, "Chờ Thanh Toán"),
    ChoGiaoHang(2, "Chờ Giao Hàng"),
    DaGiao(3, "Hoàn thành"),
    DaHuy(4, "Đã Hủy");

    private final int value;
    private final String displayName;

    BillStatus(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BillStatus fromInt(int i) {
        for (BillStatus status : values()) {
            if (status.getValue() == i) {
                return status;
            }
        }
        return null;
    }
}
