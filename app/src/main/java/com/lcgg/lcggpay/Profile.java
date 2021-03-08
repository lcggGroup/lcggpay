package com.lcgg.lcggpay;

public class Profile {
    public String username;
    public String firstName;
    public String lastName;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }

    public Profile(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
