package com.annoyedandroid.ancora.model;



public class User {

    private String email;
    private String displayName;
    private String userId;


    public User() {}

    public User(String email, String displayName, String userId) {
        this.email = email;
        this.displayName = displayName;
        this.userId = userId;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
