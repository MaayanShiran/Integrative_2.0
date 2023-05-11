package com.maayan.integrative_20.Boundaries;

public class NewUserBoundary {

    private String role;
    private String username;
    private String avatar;
    private String email;

    public NewUserBoundary() {
    }

    public NewUserBoundary(String role, String username, String avatar, String email) {
        this.role = role;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NewUserBoundary{" +
                "role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
