package com.example.binimoy;

public class BuyerSignUpHelperClass {

    private String name, number, email, username;

    public BuyerSignUpHelperClass(){

    }

    public BuyerSignUpHelperClass(String name, String number, String email, String username) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.username = username;
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
}
