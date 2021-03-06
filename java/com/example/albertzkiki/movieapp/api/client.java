package com.example.albertzkiki.movieapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by albertzkiki on 3/23/2018.
 */

public class client {

    public static final String BASE_URL="http://api.themoviedb.org/3/";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
