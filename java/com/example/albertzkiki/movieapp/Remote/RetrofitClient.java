package com.example.albertzkiki.movieapp.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by albertzkiki on 5/3/2018.
 */

public class RetrofitClient {


    public static Retrofit retrofit = null;

    public static Retrofit getClient(String baseURL){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
