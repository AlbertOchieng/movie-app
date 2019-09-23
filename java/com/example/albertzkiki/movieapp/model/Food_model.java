package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 4/4/2018.
 */

public class Food_model {


    public String name;
    public String image;
    public String funfact;
    public String price;
    public String discount;

    public Food_model() {
    }

    public Food_model(String name, String image, String funfact, String price, String discount) {
        this.name = name;
        this.image = image;
        this.funfact = funfact;
        this.price = price;
        this.discount = discount;
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

    public String getFunfact() {
        return funfact;
    }

    public void setFunfact(String funfact) {
        this.funfact = funfact;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
