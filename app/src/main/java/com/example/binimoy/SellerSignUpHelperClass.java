package com.example.binimoy;

public class SellerSignUpHelperClass {
    private String name, number, email, username,address;

    public SellerSignUpHelperClass() {

    }

    public SellerSignUpHelperClass(String name, String number, String email, String username, String address) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.username = username;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
