package com.example.movieselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSelector extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static String url="https://api.themoviedb.org";
    private SearchView searchView;
    private List<Movie.ResultsDTO> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selector);
        movies= new ArrayList<>();
        recyclerView= findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        MovieCardAdapter cardAdapter= new MovieCardAdapter(movies,this);
        recyclerView.setAdapter(cardAdapter);
        searchView= findViewById(R.id.search);
        Retrofit retrofit= new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        MovieSearchInterface movieSearch= retrofit.create(MovieSearchInterface.class);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<Movie> call= movieSearch.getMovies(query);
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie=response.body();
                        List<Movie.ResultsDTO> movieList=movie.getResults();
                        movies.addAll(movieList);
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }
}