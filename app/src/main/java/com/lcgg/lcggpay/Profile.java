package com.lcgg.lcggpay;

public class Profile {

    public String username;
    public String password;
    public String firstName;
    public String middleName;
    public String lastName;
    public String fullName;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }
    public Profile(String username, String password, String firstName, String middleName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    //Get
    public String getUsername() {
        return username;
    }
    public String getPassword() { return password; }

    //Set
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) { this.password = password; }

    public String getFullName() {
        if (middleName == "" || middleName == null){
            return firstName + " " + lastName;
        }
        else{
            return firstName + " " + middleName + " " + lastName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() { return middleName; }

    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String firstName, String middleName, String lastName) {
        if (middleName == "" || middleName == null){
            this.fullName = firstName + " " + lastName;
        }
        else{
            this.fullName = firstName + " " + middleName + " " + lastName;
        }
    }


}
