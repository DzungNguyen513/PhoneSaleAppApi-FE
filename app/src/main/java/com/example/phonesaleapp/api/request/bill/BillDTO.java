package com.example.phonesaleapp.api.request.bill;

import java.util.List;

public class BillDTO {
    private String customerId;
    private String customerName;
    private String deliveryAddress;
    private String customerPhone;
    private String note;
    private List<BillDetailDTO> billDetails;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<BillDetailDTO> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetailDTO> billDetails) {
        this.billDetails = billDetails;
    }
}
