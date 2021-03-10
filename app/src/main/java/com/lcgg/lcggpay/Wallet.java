package com.lcgg.lcggpay;

public class Wallet {
    public String description;
    public Double amount;


    public Wallet() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }

    public Wallet(String description, Double amount) {
        this.description = description;
        this.amount = amount;
    }

}
