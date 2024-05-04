package com.example.phonesaleapp.model.bill;

import java.util.List;

public class BillSummaryDTO {
    private String billId;
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
    private String note;
    List<ProductBill> lstProductBill;

    public BillSummaryDTO(String billId, String customerName, String customerPhone, String deliveryAddress, String note, List<ProductBill> lstProductBill) {
        this.billId = billId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.note = note;
        this.lstProductBill = lstProductBill;
    }
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ProductBill> getLstProductBill() {
        return lstProductBill;
    }

    public void setLstProductBill(List<ProductBill> lstProductBill) {
        this.lstProductBill = lstProductBill;
    }
}
