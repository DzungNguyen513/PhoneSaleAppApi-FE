package com.example.phonesaleapp.model;

import java.util.List;

public class Bill {
    private String billId;
    private String customerId;
    private String employeeId;
    private String dateBill;
    private int status;
    private double totalBill;
    private Customer customer;
    private Employee employee;
    private List<String> billDetails;
}
