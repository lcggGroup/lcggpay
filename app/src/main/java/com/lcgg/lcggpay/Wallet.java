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

    //Get
    public String getDescription() {
        return description;
    }

    //Set
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
