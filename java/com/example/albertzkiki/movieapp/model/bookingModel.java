package com.example.albertzkiki.movieapp.model;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by albertzkiki on 5/10/2018.
 */

public class bookingModel {

    private String name;
    private String phone;
    private String price;
    private String paymentstatus;
    private String date;
    private String time;
    private List<Integer>Seatsnumber;

    public bookingModel() {
    }

    public bookingModel(String name, String phone, String price, String paymentstatus, String date, String time, List<Integer> seatsnumber) {
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.paymentstatus = paymentstatus;
        this.date = date;
        this.time = time;
        Seatsnumber = seatsnumber;
    }

    public bookingModel(String movieName, String date, String time, List<Integer> seatsTaken) {

        this.name = name;
        this.date = date;
        this.time = time;
        Seatsnumber = seatsTaken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getSeatsnumber() {
        return Seatsnumber;
    }

    public void setSeatsnumber(List<Integer> seatsnumber) {
        Seatsnumber = seatsnumber;
    }
}
