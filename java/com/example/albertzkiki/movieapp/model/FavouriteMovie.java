package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 5/6/2018.
 */

public class FavouriteMovie {

    private String userPhone;
    private String id;
    private String name;
    private String price;
    private String image;
    private String plotsynopsis;



    public FavouriteMovie() {
    }

    public FavouriteMovie(String userPhone, String id, String name, String price, String image, String plotsynopsis) {
        this.userPhone = userPhone;
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.plotsynopsis = plotsynopsis;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlotsynopsis() {
        return plotsynopsis;
    }

    public void setPlotsynopsis(String plotsynopsis) {
        this.plotsynopsis = plotsynopsis;
    }
}
