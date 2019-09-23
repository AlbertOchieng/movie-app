package com.example.albertzkiki.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by albertzkiki on 3/23/2018.
 */

public class TrailerResponse {

    @SerializedName("id")
    private int id_trailer;
    @SerializedName("results")
    private List<Trailer> results;

    public int getIdTrailer(){
        return id_trailer;
    }

    public void setIdtrailer(int id_trailer){
        this.id_trailer = id_trailer;
    }

    public List <Trailer> getResults(){
        return results;
    }
}
