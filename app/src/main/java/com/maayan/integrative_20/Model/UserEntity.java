package com.maayan.integrative_20.Model;

public class UserEntity {


    private String userId;
    private UserRole role;
    private String userName;
    private String avatar;

    public UserEntity() {
    }


    public String getUserId() {
        return userId;
    }

    public UserRole getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }
}
