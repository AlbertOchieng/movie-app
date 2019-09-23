package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 5/3/2018.
 */

public class Sender {

    public String to;
    public Notification notification;

    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }
}
