package com.example.albertzkiki.movieapp.api;

import com.example.albertzkiki.movieapp.model.TrailerResponse;
import com.example.albertzkiki.movieapp.model.movieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by albertzkiki on 3/23/2018.
 */

public interface Server {

    @GET("movie/popular")
    Call<movieResponse> getPopularMovies(@Query("api_key")String apiKey);

    @GET("movie/top_rated")
    Call<movieResponse> getTopRatedMovies(@Query("api_key")String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
