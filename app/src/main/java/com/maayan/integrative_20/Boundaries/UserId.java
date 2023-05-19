package com.maayan.integrative_20.Boundaries;

public class UserId {

    private String springApplicationName;
    private String email;

    public UserId() {
    }

    public UserId(String email) {
        this.email = email;
    }

    public String getSuperapp() {
        return springApplicationName;
    }

    public void setSuperapp(String superapp) {
        this.springApplicationName = superapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserId{" +
                "superapp='" + springApplicationName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
