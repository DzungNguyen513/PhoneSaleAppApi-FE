package com.example.phonesaleapp.model.customer;

public class CustomerUpdateDTO {
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;
    private int gender;

    public CustomerUpdateDTO(String customerName, String phoneNumber, String email, String address, int gender) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }
}
