package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 5/2/2018.
 */

public class Token {

    private String token;
    private boolean isSeverToken;

    public Token() {
    }

    public Token(String token, boolean isSeverToken) {
        this.token = token;
        this.isSeverToken = isSeverToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSeverToken() {
        return isSeverToken;
    }

    public void setSeverToken(boolean severToken) {
        isSeverToken = severToken;
    }
}
