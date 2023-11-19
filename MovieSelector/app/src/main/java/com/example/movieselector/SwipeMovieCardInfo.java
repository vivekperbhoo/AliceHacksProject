package com.example.movieselector;

public class SwipeMovieCardInfo {
    int year;
    String posterPath, title, overview;

    SwipeMovieCardInfo(String posterPath, int year, String title, String overview){
        this.posterPath = posterPath;
        this.year = year;
        this.title = title;
        this.overview = overview;

    }
}
