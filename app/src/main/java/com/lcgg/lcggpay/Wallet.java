package com.lcgg.lcggpay;

public class Wallet {
    public String description;
    public String amount;
    public String date;


    public Wallet() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }

    public Wallet(String description, String amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

}
