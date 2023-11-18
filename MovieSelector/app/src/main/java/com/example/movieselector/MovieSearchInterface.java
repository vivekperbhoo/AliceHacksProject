package com.example.movieselector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSearchInterface {
    @GET("/3/search/movie?api_key=5c107139744b00f06e431bab60d985a9")
    Call<Movie> getMovies(
             @Query("query")String movieName
            );

}
