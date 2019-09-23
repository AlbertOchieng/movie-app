package com.example.albertzkiki.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by albertzkiki on 3/23/2018.
 */

public class movieResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("result")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<movie> results;

    public int getPage(){
        return page;
    }
    public void setPage(int page){
        this.page = page;
    }
    public int getTotalResults(){
        return totalResults;
    }
    public void setTotalResults(int totalResults){
        this.totalResults = totalResults;
    }
    public int getTotalPages(){
        return totalPages;
    }
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }
    public List<movie>getResults(){
        return results;
    }
    public void setResults(List<movie>results){
        this.results = results;
    }
}
