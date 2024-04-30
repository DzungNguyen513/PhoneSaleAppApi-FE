package com.example.phonesaleapp.model;

import com.example.phonesaleapp.adapter.bill.BillStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Bill {
    private String billId;
    private String dateBill;
    private int totalProducts;
    private int totalBill;
    private int status;

    public Bill(String billId, String dateBill, int totalProducts, int totalBill, int status) {
        this.billId = billId;
        this.dateBill = dateBill;
        this.totalProducts = totalProducts;
        this.totalBill = totalBill;
        this.status = status;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getDateBill() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = inputFormat.parse(dateBill);
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateBill;
    }

    public void setDateBill(String dateBill) {
        this.dateBill = dateBill;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public BillStatus getStatusBill() {
        return BillStatus.fromInt(status);
    }

    public void setStatusBill(BillStatus statusBill) {
        this.status = statusBill.getValue();
    }
}
