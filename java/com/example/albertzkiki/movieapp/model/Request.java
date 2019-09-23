package com.example.albertzkiki.movieapp.model;

import java.util.List;

/**
 * Created by albertzkiki on 4/8/2018.
 */

public class Request {

    private String phone;
    private String name;
    private String total;
    private String paymentstatus;
    private List<Order> foods;
    private String status;


    public Request() {
    }

    public Request(String phone, String name, String total, String paymentstatus, List<Order> foods, String status) {
        this.phone = phone;
        this.name = name;
        this.total = total;
        this.paymentstatus = paymentstatus;
        this.foods = foods;
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
