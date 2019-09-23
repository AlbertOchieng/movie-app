package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 3/22/2018.
 */

public class User {


    String name;
    String email;
    String password;
    String confirmpass;
    String phone;
    private String isStaff;

    public User() {
    }

    public User(String name, String email, String password, String confirmpass ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmpass = confirmpass;
        this.isStaff = "false";

    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public User(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpass() {
        return confirmpass;
    }

    public void setConfirmpass(String confirmpass) {
        this.confirmpass = confirmpass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}