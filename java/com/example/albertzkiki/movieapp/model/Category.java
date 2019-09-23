package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 3/24/2018.
 */

public class Category {

    private String name;
    private String image;
    private String plotsynopsis;
    private String price;

    public Category() {
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
        this.plotsynopsis = plotsynopsis;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
