package com.lcgg.lcggpay;

public class Wallet {
    public String description;
    public String amount;


    public Wallet() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }

    public Wallet(String description, String amount) {
        this.description = description;
        this.amount = amount;
    }

}
