package com.lcgg.lcggpay;

public class Profile {

    public String username;
    public String firstName;
    public String lastName;
    public String fullName;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }
    public Profile(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Get
    public String getUsername() {
        return username;
    }

    //Set
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String firstName, String lastName) {
        this.fullName = firstName + " " + lastName;
    }
}
