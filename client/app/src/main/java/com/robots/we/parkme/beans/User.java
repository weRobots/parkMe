package com.robots.we.parkme.beans;

public class User {

    private String registrationToken;

    private String vehichleNumber;

    private String mobileNumber;

    private String userId;

    private String name;

    private UserRole role;

    public User() {
        this.role = UserRole.DEFAULT;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getVehichleNumber() {
        return vehichleNumber;
    }

    public void setVehichleNumber(String vehichleNumber) {
        this.vehichleNumber = vehichleNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
