package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 4/14/2018.
 */

public class Rating {

    String userPhone;
    String movieId;
    String rating;
    String comment;
    String MovieTitle;


    public Rating() {
    }

    public Rating(String userPhone, String movieId, String rating, String comment,String movieTitle) {
        this.userPhone = userPhone;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
        this.MovieTitle =movieTitle;

    }



    public String getUserPhone() {
        return userPhone;
    }


    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
