package com.example.movieselector;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieSearchInterface {
    @GET("/3/search/{name}")
    Call<Movie> getMovies(
            @Path("name")String movieName
    );

}
